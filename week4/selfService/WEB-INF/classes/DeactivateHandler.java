import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.DaoImp;

public class DeactivateHandler extends HttpServlet {      
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {  
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        
        String updateSQL = "UPDATE Users SET active = ? WHERE email = ?";
        String[] parameters = {"0", email};
        DaoImp.update(updateSQL, parameters);
        
        session.setAttribute("active", "0");
        response.sendRedirect("inactive.jsp");
    }
    
    // Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}