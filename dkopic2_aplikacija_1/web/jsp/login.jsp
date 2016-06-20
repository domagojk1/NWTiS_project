<%-- 
    Document   : login
    Created on : Jun 5, 2016, 4:36:32 PM
    Author     : domagoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
    </head>
    <body>
        <h1>Login</h1> 
        <form action="j_security_check" method="POST"> 
            <br/>Username:<input type="text" name="j_username"> 
            <br/>Password:<input type="password" name="j_password"> 
            <br/><input type="submit" value="Login"> 
        </form> 
    </body>
</html>
