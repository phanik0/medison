<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medison</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header.css">
</head>
<body>
<header>
    <h1><a href="${pageContext.request.contextPath}/main">Medison</a></h1>
    <button class="logout-button" onclick="logout()">Logout</button>
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
                window.location.href = '${pageContext.request.contextPath}/login'; // 로그아웃 후 리디렉션할 페이지
            } else {
                alert('Logout failed. Please try again.');
            }
        });
    }
</script>
