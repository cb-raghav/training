import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;

public class DropdownListener extends HttpServlet {  // JDK 1.6 and above only

    // The doGet() runs once per HTTP GET request to this servlet.
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the MIME type and character encoding for the response message
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Get a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        Map< String, List<String>> locations = new HashMap<>();
        List<String> indianStates = Arrays.asList("Tamil Nadu", "Karnataka", "West Bengal",
                "Kerala", "Andhra Pradesh", "Maharashtra");
        List<String> americanStates = Arrays.asList("Alabama", "Washington", "California",
                "North Carolina", "New York", "Texas");
        List<String> englishStates = Arrays.asList("Kent", "Lancashire", "Yorkshire", "Devon", 
                "Surrey", "Norfolk");
        List<String> australianStates = Arrays.asList("New South Wales", "Queensland", "Tasmania",
                "Victoria", "ACT", "South Australia");

        locations.put("India", indianStates);
        locations.put("United States of America", americanStates);
        locations.put("England", englishStates);
        locations.put("Australia", australianStates);

        String json = new Gson().toJson(locations);
        out.write(json); //System.out.println(json);
    }

}
