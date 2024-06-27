<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Study DICOM Viewer</title>
    <!-- CornerstoneJS 및 DICOM Image Loader를 포함합니다. -->
    <script src="https://unpkg.com/cornerstone-core"></script>
    <script src="https://unpkg.com/@cornerstonejs/dicom-image-loader"></script>
    <script src="https://unpkg.com/dicom-parser"></script>
    <script src="${pageContext.request.contextPath}/script/study.js"></script>
</head>
<body>
<h1>Study DICOM Viewer</h1>
<div id="dicomImage" style="width:512px; height:512px; border:1px solid black;"></div>
<script>
    // 서버에서 DICOM 파일 경로를 전달받습니다.
    <c:if test="${not empty dicomPath}">
    const dicomPath = 'wadouri:' + '${pageContext.request.contextPath}/dicom-file?path=' + encodeURIComponent('<c:out value="${dicomPath}" />');
    console.log(dicomPath); // 디버깅을 위해 추가
    window.onload = function() {
        initializeDicomViewer(dicomPath);
    };
    </c:if>
</script>
</body>
</html>
