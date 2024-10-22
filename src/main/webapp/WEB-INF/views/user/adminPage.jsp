
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../module/header.jsp"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/adminPage.css">
</head>
<body>
<div id="main-content">
    <c:if test="${not user.admin}">
        <c:redirect url="/check"></c:redirect>
    </c:if>
    <div class="button-container">
        <button onclick="location.href='/admin/manage'">회원관리</button>
        <button onclick="location.href='/log'">출입로그확인</button>
        <button onclick="location.href='/check'">메인페이지</button>
    </div>
</div>

</body>
</html>

