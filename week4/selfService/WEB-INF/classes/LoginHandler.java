import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.lang3.RandomStringUtils;
import dao.DaoImp;
import util.UtilFunctions;

public class LoginHandler extends HttpServlet {      
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	// Fetch the input values from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = "";
        
        // Validate the input
        boolean inputValid = true; 
        /*
        List of validations:
        1. All fields shouldn't be empty or null
        2. Email should have right format (regex)   
        */
        
        boolean eFlag = false, pFlag = false;
        eFlag = !UtilFunctions.isNullEmpty(email);
        pFlag = !UtilFunctions.isNullEmpty(password);
        boolean flag = (eFlag && pFlag);
        
        boolean emailPattern = false, pwdPattern = false;
        String emailRegex = "^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$";
        String pwdRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
        emailPattern = UtilFunctions.matchRegex(email, emailRegex);
        pwdPattern = UtilFunctions.matchRegex(password, pwdRegex);
        
        if(flag && emailPattern && pwdPattern) {
            inputValid = true;
        }
        else {
            inputValid = false;
        }
        
        PrintWriter out = response.getWriter();
        String resultMsg = ""; String targetPage = "";
        if(inputValid) { // Check if email and password match by querying the DB
            String selectSQL = "SELECT * FROM Users WHERE email = ? AND password = ?";
            String[] parameters = {email, password};
            List< HashMap<String, Object>> result  = DaoImp.select(selectSQL, parameters);
            
            boolean matchFound = !result.isEmpty();
            if(matchFound) { // if match is found, create cookie for user and load the welcome page
                HashMap<String, Object> row = result.get(0);
                firstName = (String) row.get("fname");
                
                targetPage = "welcome.jsp"; 
                Cookie loginCookie = new Cookie("user", RandomStringUtils.randomAlphanumeric(32));
                loginCookie.setMaxAge(60 * 60 * 24);
                
                // create a new session
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("firstName", firstName);
                session.setMaxInactiveInterval(15 * 60);
                
                response.addCookie(loginCookie);
                System.out.println("\nCookie " + loginCookie.getName() +  " created with value " + loginCookie.getValue() + " by LoginHandler!");
                response.sendRedirect(targetPage);
            }
            else { // Display error message and reload the login page
                out.println("<p style = \"color: red; text-align: center;\"> ERROR: credentials didn't match!</p>");
                targetPage = "login.html";
                
                RequestDispatcher rd = request.getRequestDispatcher(targetPage);
                rd.include(request, response);
            }
        }
        else { // Invalid input; display corresponding error message
            out.println("<p style = \"color: red; text-align: center;\"> ERROR: invalid input!</p>");
            targetPage = "login.html";

            RequestDispatcher rd = request.getRequestDispatcher(targetPage);
            rd.include(request, response);
        }
        out.close();
    }  
    //Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
        doPost(req, resp);  
    }  
}