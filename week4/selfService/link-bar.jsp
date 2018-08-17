<html>
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #9999ff;
        }

        li {
            float:left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a {
            display: block;
            color: white;
            text-decoration: none;
        }

        li a:hover {
            background-color: #ccccff;
        }
    </style>

    <body>
        <%
            String firstName = (String) session.getAttribute("firstName");
            String active = (String) session.getAttribute("active");
        %>

        <ul>
            <li>Self Service Portal</li>
            <li style="float:right"><a href="LogoutHandler">Logout</a></li>
        <% if(active.equals("1")) { %>
            <li style="float:right"><a href="DeactivateHandler">Deactivate Account</a></li>
        <% } %>    
            <li style="float:right">Hi <%= firstName %>!</li>
        </ul>

    </body>
</html>
