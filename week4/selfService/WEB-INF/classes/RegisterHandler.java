import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import util.UtilFunctions;
import dao.DaoImp;

public class RegisterHandler extends HttpServlet {      
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
	// Fetch the input values from request
        String firstName = request.getParameter("firstName");  
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email"); String emailConfirm = request.getParameter("emailConfirm");
        String password = request.getParameter("password"); String passwordConfirm = request.getParameter("passwordConfirm");
        String address = "";
        
        // Validate the input
        boolean inputValid = true;
        /*
        List of validations:
        1. All fields shouldn't be empty or null
        2. Email and password should have right format (regex)
        3. Email and confirm email should match
        4. Password and confirm password should match
        */
        boolean fnFlag = false, lnFlag = false, eFlag = false, pFlag = false;
        boolean eMatch = false, pMatch = false;
        
        fnFlag = !UtilFunctions.isNullEmpty(firstName);
        lnFlag = !UtilFunctions.isNullEmpty(lastName);
        eFlag = !UtilFunctions.isNullEmpty(email);
        pFlag = !UtilFunctions.isNullEmpty(password);
        boolean flag = (fnFlag && lnFlag && eFlag && pFlag);
        
        eMatch = email.equals(emailConfirm);
        pMatch = password.equals(passwordConfirm);
        
        boolean emailPattern = false, pwdPattern = false;
        String emailRegex = "^[\\w-]+(?:\\.[\\w-]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$";
        String pwdRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
        emailPattern = UtilFunctions.matchRegex(email, emailRegex);
        pwdPattern = UtilFunctions.matchRegex(password, pwdRegex);
        
        if(flag && emailPattern && eMatch && pwdPattern && pMatch) {
            inputValid = true;
        }
        else {
            inputValid = false;
        }
        
        PrintWriter out = response.getWriter();
        String resultMsg = ""; String targetPage = "index.html";
        if(inputValid) {
            String insertSQL = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?)";
            String[] parameters = {email, password, firstName, lastName, address, "1"};
            String queryResult = DaoImp.update(insertSQL, parameters);
            if(queryResult.equals("SUCCESS!")) {
                resultMsg = "<p style = \"color: blue; text-align: center;\">Successfully registered new user!</p>";
                targetPage = "login.html";
            }
            else if(queryResult.contains("MySQLIntegrityConstraintViolationException")) {
                // record violates primary key constraint
                resultMsg = "<p style = \"color: red; text-align: center;\"> ERROR: the user already exists!</p>";
            }
            else {
                resultMsg = "<p style = \"color: red; text-align: center;\"> ERROR: failed to register user!</p>";
            }
            out.println(resultMsg);
        }
        else {
            out.println("<p style = \"color: red; text-align: center;\">Invalid input!</p>");
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(targetPage);
        rd.include(request, response);
        
        out.close();
    }  
    //Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
        doPost(req, resp);  
    }  
}