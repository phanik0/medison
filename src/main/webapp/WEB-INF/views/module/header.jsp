<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/128/5540/5540514.png">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <title>Medison</title>
</head>
<body>
<header>
    <h1>
        <a href="${pageContext.request.contextPath}/main">
            <img src="${pageContext.request.contextPath}/image/logo.png" style="height: 50px;">
        </a>
    </h1>
    <button onclick="logout()">Logout</button>
</header>
<script>
    function logout() {
        fetch('${pageContext.request.contextPath}/user/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(response => {
            if (response.ok) {
                window.location.href = '${pageContext.request.contextPath}/user/login'; // 로그아웃 후 리디렉션할 페이지
            } else {
                alert('Logout failed. Please try again.');
            }
        });
    }
</script>
</body>
</html>
