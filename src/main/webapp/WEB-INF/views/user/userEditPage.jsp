<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 2024-07-01
  Time: 오전 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../module/header.jsp" />
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/manage.css">
    <script src="${pageContext.request.contextPath}/script/user-edit.js"></script>
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="/user/login"></c:redirect>
</c:if>

<div id="user-update-container" class="container"
     data-user-id="${user.id}">
    <h1 class="title">${user.id} 수정</h1>
    <form id="registerForm">
        <div class="form-group">
            <label for="cur-password">현재 비밀번호</label>
            <input type="password" id="cur-password" name="cur-password" >
            <label for="new-password">변경할 비밀번호</label>
            <input type="password" id="new-password" name="new-password" >
        </div>
        <div class="form-actions">
            <button type="submit" class="confirm-button">수정하기</button>
        </div>
    </form>
</div>

</body>
</html>
