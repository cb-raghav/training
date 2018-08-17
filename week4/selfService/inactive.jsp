<html>
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