<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="module/header.jsp" />
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/manage.css">
</head>
<body>
<button class="add-button">추가</button>
<div class="profile-container">
    <c:forEach var="doctor" items="${infos}">
        <c:if test="${not doctor.admin}">
        <div class="profile-card">
            <div class="profile-image"></div>
<%--            <form method="delete" action="/user/delete">--%>
<%--                <input type="hidden" name="id" id="id" value="${doctor.id}">--%>
<%--                <input type="submit" value="-">--%>
<%--            </form>--%>
            <div class="profile-details">
                <p>이름 : ${doctor.name}</p>
                <p>직급 : ${doctor.position.equals("professor")?"교수":doctor.position.equals("intern")?"인턴":doctor.position.equals("fellow")?"펠로우":"레지던트"}</p>
                <p>아이디 : ${doctor.id}</p>
                <p>연락처 : ${doctor.phone}</p>
            </div>
        </div>
        </c:if>
    </c:forEach>
</div>

</body>
</html>
