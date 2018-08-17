<%@ page import = "java.io.*, java.util.*, java.lang.*"%>

<html>
    <head>  
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        
        <title>Edit details</title>
        <style>
            input:disabled {
                background: #dddddd;
            }
        </style>
        
        <script>
            $(document).ready(function () {
                var responseJson = null;
                $.get('DropdownListener', function (data) { 
                    responseJson = data;
                    $('#countries').empty();
                    $('<option disabled selected>').text("Select country").appendTo($("#countries")); 
                    $.each(responseJson, function (key, value) {
                        $('<option>').text(key).appendTo($("#countries")); 
                    });
                });
                
                $('#countries').change(function (event) { 
                    var country = $("#countries").val();
                    var $states = $('#states');
                    $.each(responseJson, function (key, value) {
                        var arr = value;
                        if (key == country) {
                            $states.empty();
                            $.each(arr, function (index, value) {
                                $('<option>').text(value).appendTo($states); 
                            });
                        }
                    });
                });
            });
        </script>
    </head>
    
    <%
        String email = (String) session.getAttribute("email");
    %>
        
    <body>
        <jsp:include page="link-bar.jsp" />
        
        <div align='center'>
            <h2>Edit Details</h2>
            <form action="UpdateHandler">
                <input type="text" name="firstName" placeholder="First Name"><br>
                <input type="text" name="lastName" placeholder="Last Name"><br>
                <input type="text" name="emailDisp" value="<%= email %>" disabled="disabled"/><br>
                <input type="text" name="addr1" placeholder="Address line 1"><br>
                <input type="text" name="addr2" placeholder="Address line 2"><br>
                <input type="text" name="city" placeholder="City">
                &nbsp;
                <input type="text" name="zip" placeholder="ZIP">
                <br>
                <select id="countries" name="country">
                    
                </select>
                &nbsp;
                <select id="states" name="state">
                    
                </select>
                <br><br><br>
                <input type="hidden" name="email" value="<%= email %>" />
                <input type="submit" value="Save details"/> 
                &nbsp;
                <a href="welcome.jsp">Discard edits</a> 
            </form>    
        </div>   
        
    </body>
</html>