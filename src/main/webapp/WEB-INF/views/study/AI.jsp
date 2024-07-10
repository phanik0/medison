<%--
  Created by IntelliJ IDEA.
  User: TJ
  Date: 24. 7. 8.
  Time: 오후 5:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/ai.js"></script>
    <title>Title</title>
</head>
<body>
<div id="main-viewer">
    <!-- Main image viewer -->
    <div id="viewportGrid">
        <canvas id="overlay"></canvas> <!-- Add the overlay canvas element -->
    </div>
</div>
</body>
</html>
