<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
    <c:choose>
        <c:when test="${empty user}">
            <c:redirect url="/user/login"></c:redirect>
        </c:when>
        <c:otherwise>
            <c:redirect url="/admin"></c:redirect>
        </c:otherwise>
    </c:choose>
</body>
</html>
