<%@ page import = "java.io.*, java.util.*, java.lang.*"%>

<html>
    <head>  
        <title>Edit details</title>
        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            
            div#form {
                position: absolute;
                width: 400px;
                height: 500px;
                left: 50%;
                margin: 0px -200px;
                //border: solid;
                text-align: center;
            }
            
            label {
                display: inline-block;
                width: 20%;
                font-variant: small-caps;
                font-size: 14px;
                padding: 4px 8px;
                color: grey;
                float: left;
            }
            
            .input-container {
                display: flex;
                width: 100%;
                padding: 0px 0px 0px 0px;
            }
            
            .input-field {
                width: 80%;
                padding: 4px 8px;
            }
            
            input:disabled {
                background: #dddddd;
            }
            
            input[type=submit] {
                width: 50%;
                margin: 0 auto;
                background-color: #ff8080;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                font-variant: small-caps;
                border: none;
                border-radius: 2px;
                cursor: pointer;
            }
            
            select {
                appearance: none; 
                height: 25px;   
            }
            
        </style>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                var responseJson = null;
                $.get('DropdownListener', function (data) { 
                    responseJson = data;
                    $('#countries').empty();
                    $('<option disabled selected>').text("Country").appendTo($("#countries")); 
                    $('<option disabled selected>').text("State").appendTo($("#states")); 
                    $.each(responseJson, function (key, value) {
                        $('<option>').text(key).appendTo($("#countries")); 
                    });
                });
                
                var queryString = $(location).attr("search");
                queryString = queryString.replace('?', '');
                var hashes = queryString.split('&'); 
                var queryParams = [];
                for(var i = 0; i < hashes.length; i++) {
                    var hash = hashes[i].split('=');
                    queryParams.push(hash[0]);
                    queryParams[hash[0]] = hash[1];
                }
                
                setTimeout(function() {
                    //alert(queryString + "\n" + hashes + "\n" + queryParams);
                    for(var i = 0; i < queryParams.length; i++) {
                        if(queryParams[i] == "invalidInput") {
                            if(queryParams[queryParams[i]] == "true") {
                                alert("ERROR: Invalid input! Please re-enter ...");
                            }
                        }
                    }
                }, 300);
                
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
        
        <div id="form" align='center'>
            <h2>Edit Details</h2>
            <br>
            <form action="UpdateHandler">
                <div class="input-container" >
                    <label>Name</label>
                    <input class="input-field" type="text" name="firstName" placeholder="First Name"
                           required>                    
                </div>
                <br>
                <div class="input-container" >
                    <label></label>
                    <input class="input-field" type="text" name="lastName" placeholder="Last Name"
                           required>                    
                </div>
                <br>
                <div class="input-container">
                    <label>Email</label>
                    <input class="input-field" type="text" name="emailDisp" value="<%= email %>" disabled="disabled"/><br>
                </div>
                <br>
                <div class="input-container">
                    <label>Address</label>
                    <input type="text" class="input-field" name="addr1" placeholder="Address line 1"
                           required>
                </div> 
                <br>
                <div class="input-container">
                    <label></label>
                    <input type="text" class="input-field" name="addr2" placeholder="Address line 2"
                           required>
                </div>
                <br>
                <div class="input-container" style="width: 307.37px; padding: 0px 0px 0px 92.63px">
                    <input type="text" class="input-field" name="city" placeholder="City"
                           required>
                    &nbsp;&nbsp;
                    <select class="input-field" id="states" name="state">

                    </select>
                    
                </div>  
                <br>
                <div class="input-container" style="width: 307.37px; padding: 0px 0px 0px 92.63px">
                    <input type="text" class="input-field" name="zip" placeholder="ZIP"
                           required>
                    &nbsp;
                    <select class="input-field" id="countries" name="country">
                    
                    </select>
                </div> 
                <br>
                <input type="hidden" name="email" value="<%= email %>" />
                <input type="submit" value="Save details"/> 
                <br>
                <a href="welcome.jsp" style="font-size: 12px;">Discard edits</a> 
            </form>    
        </div>   
        
    </body>
</html>