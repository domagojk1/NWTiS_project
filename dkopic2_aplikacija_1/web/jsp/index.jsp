<%-- 
    Document   : index
    Created on : Jun 5, 2016, 12:49:20 AM
    Author     : domagoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aplikacija 1</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
    </head>
    <body>
        <h3>Admin</h3>
        <ul>
            <li><a href="jsp/admin/users.jsp">Pregled korisnika</a></li>
            <li><a href="jsp/admin/log.jsp">Pregled dnevnika</a></li>
            <li><a href="jsp/admin/requests.jsp">Pregled zahtjeva</a></li>
        </ul>
        
        <h3>Korisnik</h3>
        <ul>
            <li><a href="jsp/user/user_requests.jsp">Pregled vlastitih zahtjeva</a></li>
        </ul>
    </body>
</html>
