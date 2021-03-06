<%-- 
    Document   : requests
    Created on : Jun 5, 2016, 1:08:58 AM
    Author     : domagoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<jsp:useBean id="logData" class="org.foi.nwtis.dkopic2.jspbeans.UserRequestsBean" scope="request" />
<jsp:setProperty name="logData" property="request" value="${pageContext.request}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/displaytag.css" />
        <title>Pregled dnevnika zahtjeva</title>
    </head>
    <body>
        <h1>Vlastiti zahtjevi za server socket</h1>
        <form action="user_requests.jsp">
            Adresa: <input type="text" name="address"/>
            Od (dd.MM.yyyy HH:mm:ss): <input type="text" name="dateStart"/>
            Do (dd.MM.yyyy HH:mm:ss): <input type="text" name="dateEnd"/>
            <br/>
            <input type="submit" name="submit" value=" Filtriraj "/> 
        </form>
        <display:table id="log" name="${logData.logs}" pagesize="${logData.lines}">
            <display:setProperty name="basic.show.header" value="true"/>
            <display:column property="user.username" title="Korisnik" sortable="true"/>
            <display:column property="action" title="Akcija" sortable="true"/>
            <display:column property="ip" title="IP adresa" sortable="true"/>
            <display:column property="time" title="Vrijeme" format="{0,date,dd.MM.yyyy HH:mm:ss}" sortable="true"/>
            <display:column property="duration" title="Trajanje (ms)" sortable="true"/>
            <display:column property="status" title="Status" sortable="true"/>
            <display:column property="address" title="Adresa" sortable="true"/>
        </display:table>
    </body>
</html>
