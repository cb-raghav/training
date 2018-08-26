import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.DaoImp;

public class ReactivateHandler extends HttpServlet {      
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {  
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        String updateSQL = "UPDATE Users SET active = ? WHERE email = ?";
        String[] parameters = {"1", email};
        DaoImp.update(updateSQL, parameters);
        
        session.setAttribute("active", "1");
        response.sendRedirect("welcome.jsp");
    }
    
    // Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}