<html>
    <head>
        <title>Inactive</title>
        
        <style>
            body {
                font-family: Arial, Helvetica, sans-serif;
            }
            
            div#details {
                text-align: center;
            }
            
            input[type=submit] {
                width: 20%;
                background-color: #ff8080;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                border-radius: 2px;
                cursor: pointer;
            }
        </style>
    </head>
    
    <body>
        <jsp:include page="link-bar.jsp" />
        
        <div align='center'>
            <h2>Uh oh!</h2>
            
            <p>It seems your account is inactive at the moment. You
                won't be able to access any user pages ...</p>
            
            <p>Click the button below to reactivate your account</p>
            
            <form action="ReactivateHandler">
                <input type="submit" value="Reactivate account"/>  
            </form>    
            
        </div>    
    </body>

</html>