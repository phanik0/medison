<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/login.css">
</head>
<body>

<div class="login-container">
    <img src="${pageContext.request.contextPath}/image/logo.png">
    <form action="/user/login" method="post">
        <input type="text" name="id" placeholder="아이디" >
        <input type="password" name="password" placeholder="비밀번호" >
        <input type="submit" value="로그인">
    </form>

        <!-- Error message will be displayed here if login fails -->
        <c:if test="${not empty loginError }">
            <script>
                alert("아이디 혹은 비밀번호를 다시 확인해 주세요.")
            </script>
            <%
                session.removeAttribute("loginError");
            %>
        </c:if>
</div>
</body>
</html>
