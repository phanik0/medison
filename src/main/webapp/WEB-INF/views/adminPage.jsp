<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 2024-06-27
  Time: 오후 3:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="module/header.jsp" />
<html>
<head>
</head>
<body>
    <c:if test="${not user.admin}">
        <c:redirect url="/main"></c:redirect>
    </c:if>
    <button onclick="location.href='http://localhost:8080/admin/manage'">회원관리</button>
    <button onclick="location.href='http://localhost:8080/main'">메인페이지</button>
</body>
</html>
