<%-- 
    Document   : processview
    Created on : 23-Aug-2015, 20:48:03
    Author     : Kelli
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GPC Processes</title>
        <link href="style/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div id ="banner">gpc</div>
        <div id="hosts-panel">

            <c:forEach items="${hosts}" var="current" varStatus="">
                <div id="host-block">
                    <a href="http://localhost:8080/galileo/p_viewer?id=${current.csName}">
                        <img src="images/flat.png" alt="host_ico" width="70" height="70">
                        <span>${current.csName}</span></a>
                </div>

            </c:forEach>
        </div>
    </body>
</html>
