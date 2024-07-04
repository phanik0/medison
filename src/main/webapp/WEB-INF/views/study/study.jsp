<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Study DICOM Viewer</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/study.css">
    <script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>

</head>
<body>
<h1>Study DICOM Viewer</h1>
<div id="thumbnail-list">
    <h2>PastStudyList</h2>
    <div>
        <select id="studySelector"></select>
    </div>
    <h2>썸네일</h2>
    <!-- Thumbnails will be added here dynamically -->
</div>
<div id="main-viewer">
    <!-- Main image viewer -->
    <div id="viewportGrid">

    </div>
</div>
<input type="hidden" id="studyKey" value="${study.studykey}">
<button id="report" ></button>
<c:out value="${study}"></c:out>

<c:out value="${study.studykey}"></c:out>
</body>
</html>
