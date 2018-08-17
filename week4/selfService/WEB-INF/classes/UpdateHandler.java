import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DaoImp;

public class UpdateHandler extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/self_service";
        // Database credentials
        final String USER = "root";
        final String PASS = "";

        
        // Fetch the input values from request
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String addr1 = request.getParameter("addr1");
        String addr2 = request.getParameter("addr2");
        String city = request.getParameter("city");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String state = request.getParameter("state");

        // Validate the input
        boolean inputValid = true;
        /*
        List of validations:
        1. All fields shouldn't be empty or null
        2. Email should have right format (regex)
        boolean eFlag = false, pFlag = false;        
        */

        String resultMsg = ""; String targetPage = "";
        if (inputValid) {
            String updateSQL = "UPDATE Users SET fname = ?, lname = ?, address = ? WHERE email = ?";
            String address = addr1 + ", " + addr2 + "; " + city + " " + zip + "; " + state + ", " + country; 
            String[] parameters = {firstName, lastName, address, email};
            DaoImp.update(updateSQL, parameters);
            
            targetPage = "welcome.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(targetPage);
            rd.forward(request, response);
        }
    }

    // Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
