<%@ page import = "java.io.*, java.util.*, java.sql.*"%>
<%@ page import = "javax.servlet.*, javax.servlet.http.*"%>
<%@ page import = "dao.DaoImp"%>

<html>
    <head>
        <title>Welcome!</title>
        
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
            }
            
            div#details {
                position: absolute;
                width: 500px;
                height: 200px;
                left: 50%;
                margin: 0px -250px;
                border: transparent;
                text-align: center;
            }
            
            table {
                border: transparent;
                width: 60%;
            }
            
            td {
                padding: 5px;
            }
            
            .row-label {
                font-variant: small-caps;
                font-size: 14px;
                color: grey;
            }
            
            input[type=submit] {
                width: 40%;
                background-color: #ff8080;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                font-variant: small-caps;
                border: none;
                border-radius: 2px;
                cursor: pointer;
            }
        </style>
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
                if(address == null || address.length() == 0) {
                    address = "-- NA --";
                }
            }
        %>

        <jsp:include page="link-bar.jsp" />

        <div id="details">
            <h2>Welcome to the Self Service Portal!</h2>
            <br>
            <table align="center">
                <tr>
                    <td class="row-label">Name</td>
                    <td><%= fullName %></td>
                </tr>
                <tr>
                    <td class="row-label">Email</td>
                    <td><%= email %></td>
                </tr>
                <tr>
                    <td class="row-label">Address</td>
                    <td> <%= address %></td>
                </tr>
            </table>
            <br>
            <form action="edit.jsp">
                <input type="hidden" name="email" value="<%= email %>" />
                <input type="submit" value="Edit details"/>  
            </form>    
        </div>

    </body>

</html>