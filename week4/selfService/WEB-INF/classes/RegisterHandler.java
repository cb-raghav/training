import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterHandler extends HttpServlet {      
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
	// JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        final String DB_URL = "jdbc:mysql://localhost:3306/self_service";
        // Database credentials
        final String USER = "root";
        final String PASS = "";
        
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
        2. Email should have right format (regex)
        3. Email and confirm email should match
        4. Password and confirm password should match
        boolean fnFlag = false, lnFlag = false, eFlag = false, pFlag = false; 
        if(firstName != null && !firstName.isEmpty()) {
            fnFlag = true;
        }
        else { 
            fnFlag = false; 
        }
        */
        
        PrintWriter out = response.getWriter();
        String resultMsg = ""; String targetPage = "index.html";
        if(inputValid) {
            Connection conn = null;
            PreparedStatement preparedStatement = null;
            try {
                // Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Open a connection
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                // Load the INSERT query into the prepared statement
                String insertSQL = "INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?)";
                preparedStatement = conn.prepareStatement(insertSQL);

                // Set parameters for the INSERT query
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, address);
                preparedStatement.setString(6, "1");

                // Execute the prepared statement 
                int numRec = preparedStatement.executeUpdate();
                resultMsg = "<p style = \"color: blue\">Successfully registered new user!</p>";
                targetPage = "login.jsp";
            }
            catch(SQLException se) {
                //Handle errors for JDBC
                String message = se.toString();
                if(message.contains("MySQLIntegrityConstraintViolationException")) {
                    // record violates primary key constraint
                    resultMsg = "<p style = \"color: red; text-align: center;\"> ERROR: the user already exists!</p>";
                    //System.out.println("ERROR: the record already exists in the 'directory' table");
                }
                else {
                    resultMsg = "<p style = \"color: red; text-align: center;\"> ERROR: failed to register!</p>";
                }
            }
            catch(Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            }
            finally {
                //finally block used to close resources
                if(preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        //ex.printStackTrace();
                    }
                }
                if(conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        //ex.printStackTrace();
                    }
                }
            } // end try-catch-finally 
            out.println(resultMsg);
        }
        else {
            out.println("<p style = \"color: red; text-align: center\">Invalid input!</p>");
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