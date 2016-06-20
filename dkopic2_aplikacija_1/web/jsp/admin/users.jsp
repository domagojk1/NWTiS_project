<%-- 
    Document   : pregledDnevnika
    Created on : Jun 5, 2016, 1:04:02 AM
    Author     : domagoj
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="use" class="org.foi.nwtis.dkopic2.jspbeans.UsersBean"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
        <title>Pregled korisnika</title>
    </head>
    <body>
        <h1>Korisnici</h1>
        <table class="table">
            <tr>
                <th>ID</th>
                <th>Korisničko ime</th>
                <th>Lozinka</th>
                <th>Uloga</th>
                <th>Kategorija</th>
                <th>Broj zahtjeva</th>
            </tr>
            
            <c:forEach items="${use.users}" var="user">
                <tr>
                    <td>
                        <c:out value="${user.id}"/>
                    </td>
                    <td>
                        <c:out value="${user.username}"/>
                    </td>
                    <td>
                        <c:out value="${user.password}"/>
                    </td>
                    <td>
                        <c:out value="${user.role}"/>
                    </td>
                    <td>
                        <c:out value="${user.category}"/>
                    </td>
                    <td>
                        <c:out value="${user.requests}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>
