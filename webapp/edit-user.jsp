<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mihail
  Date: 11/10/18
  Time: 9:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="user" value="${sessionScope.get('login')}"/>
<html>
<head>
    <title>Edit ${user.name}</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/nicofog-style.css" rel="stylesheet">
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="img/favicon.ico" type="image/x-icon">
</head>
<body class="masthead">
<div class="row" style="text-align: right">
    <div class="col-4" id="about-edit">
        Edit <c:out value='${user.name}'/> profile
    </div>
    <div class="col-7">

        <input type="hidden" id="user_id" value='${user.id}'/>
        Name: <input type="text" id="user_name" value="${user.name}">
        <br> Role: <input type="text" id="role" value='${user.role}'/>
        <br> Cigarette price: <input type="number" id="price" value='${user.cigarettePrice}'/>
        <br>

        <div class="padding"><input type="submit" id="confirm_edit" value="Edit"></div>

    </div>
    <div class="col-1"></div>
</div>
<!-- Navigation -->
<div class="menu" id="mainNav">
    <a href="user-page.jsp">main page</a> |
    <a href="admin-page.jsp">admin page</a>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/admin.js"></script>

</body>
</html>
