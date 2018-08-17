<%@ page import = "java.io.*, java.util.*, java.sql.*"%>
<%@ page import = "javax.servlet.*, javax.servlet.http.*"%>
<%@ page import = "dao.DaoImp"%>

<html>
    <head>
        <title>Welcome!</title>
    </head>

    <body>
        <%
            String firstName = "", lastName = "", address = "";
            String fullName = "";
            String email = (String) session.getAttribute("email");

            String selectSQL = "SELECT * FROM Users WHERE email = ?";
            String[] parameters = {email};
            List< HashMap<String, Object>> result  = DaoImp.select(selectSQL, parameters);
            for (int i = 0; i < result.size(); i++) {
                HashMap<String, Object> row = result.get(i);
                firstName = (String) row.get("fname");
                lastName = (String) row.get("lname");
                if(firstName != null && lastName != null) {
                    fullName = firstName + " " + lastName;
                }    
                address = (String) row.get("address");
                if(address == null || address == "") {
                    address = "-- NA --";
                }
            }
        %>

        <jsp:include page="link-bar.jsp" />

        <div align='center'>
            <h2>Welcome to the Self Service Portal!</h2>
            Name: <%= fullName %><br>
            Email: <%= email %><br>
            Address: <%= address %>	
            <br><br> 
            <form action="edit.jsp">
                <input type="hidden" name="email" value="<%= email %>" />
                <input type="submit" value="Edit details"/>  
            </form>    
        </div>

    </body>

</html>