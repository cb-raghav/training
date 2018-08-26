import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.DaoImp;

public class AuthFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        //this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        
        System.out.println("URI of request: " + uri);
        if (session == null) { // no active session
            System.out.println("No active session!");
            if ((uri.endsWith("html"))
                    || uri.endsWith("selfService/")
                    || uri.contains("LoginHandler")
                    || uri.contains("RegisterHandler")) {
                // do not perform any authentication and simply pass the request along
                System.out.println("No authentication needed, "
                        + "simply passing the request along ¯\\_(ツ)_/¯");
                chain.doFilter(request, response);
            } else {
                // if access is requested to some user page / servlet, redirect to login 
                PrintWriter out = resp.getWriter();
                out.println("<p style = \"color: red; text-align: center;\">"
                        + "ERROR: unauthorized access! Please login ...</p>");
                RequestDispatcher rd = req.getRequestDispatcher("login.html");
                rd.include(req, resp);
            }
        } else { // a session is active
            System.out.println("There is an active session with ID: " + session.getId());
            String email = (String) session.getAttribute("email");
            
            Cookie[] cookies = req.getCookies();
            boolean cookiesValid = false, accountActive = false;
            
            // check cookie validity 
            boolean flag1 = false, flag2 = false; 
            if(cookies != null) {
                for(Cookie c : cookies) {
                    String name = c.getName();
                    if(name.equals("JSESSIONID")) {
                        flag1 = true;
                    }
                    else if(name.equals("user")) {
                        flag2 = true;
                    }
                }
                if(flag1 && flag2) {
                    cookiesValid = true;
                }
            }
            
            // check if account is active
            String active = "";
            String selectSQL = "SELECT * FROM Users WHERE email = ?";
            String[] parameters = {email};
            
            List< HashMap<String, Object>> result  = DaoImp.select(selectSQL, parameters);
            HashMap<String, Object> row = result.get(0);
            active = (String) row.get("active");
            session.setAttribute("active", active);
            accountActive = active.equals("1");
            
            if(cookiesValid) {
                if(accountActive) {
                    if(uri.endsWith("login.html") || uri.endsWith("inactive.jsp")) {
                        resp.sendRedirect("welcome.jsp");
                    }
                    else {
                        chain.doFilter(request, response);
                    }
                } else {
                    if( (uri.endsWith("jsp") && !uri.endsWith("inactive.jsp")) 
                            || uri.endsWith("login.html")) {
                        resp.sendRedirect("inactive.jsp");
                    }
                    else {
                        chain.doFilter(request, response);
                    }
                }
            }
        }
    }

    public void destroy() {
        //close any resources here
    }

}
