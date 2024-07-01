<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Study DICOM Viewer</title>
    <script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</head>
<body>
<h1>Study DICOM Viewer</h1>
<div id="content">
    <div id="studykey" value = "${study.studykey}">${study.studykey}</div>

</div>

</body>
</html>
