
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutHandler extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Cookie name: " + cookie.getName());
                if ((cookie.getName()).equals("user")) {
                    loginCookie = cookie;
                    System.out.println("Cookie " + loginCookie.getName() + " with value " + loginCookie.getValue() + " will be deleted by LogoutHandler!");
                    break;
                }
            }
        }
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            System.out.println("Cookie " + loginCookie.getName() + " with value " + loginCookie.getValue() + " was deleted by LogoutHandler!");
            response.addCookie(loginCookie);
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("login.html");
    }
    //Override  

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
