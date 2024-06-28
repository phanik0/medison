<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<html>
<head>
    <title>Medison</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="icon" href="https://cdn-icons-png.flaticon.com/128/5540/5540514.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header.css">
    <script src="${pageContext.request.contextPath}/script/note.js"></script>
</head>
<body>
<header><img src="${pageContext.request.contextPath}/image/logo.png"></header>

<h1>소견서</h1>
<form id="noteForm" action="/note/${note.studykey}" method="POST">
    <div id="note-details">
        <table>
<%--            <tr>--%>
<%--                <th>코드</th>--%>
<%--                <td id="code">${note.code}</td>--%>
<%--            </tr>--%>
            <tr>
                <th>스터디키</th>
                <td id="studykey">${note.studykey}</td>
            </tr>
            <tr>
                <th>최종 담당 의사</th>
                <td id="finalDoctor">${note.finalDoctor}</td>
            </tr>
            <tr>
                <th>환자 코드</th>
                <td id="patientCode">${note.patientCode}</td>
            </tr>
            <tr>
                <th>질병</th>
                <td><input type="text" id="disease" value="${note.disease}"></td>
            </tr>
            <tr>
                <th>치료 기간</th>
                <td><input type="text" id="treatmentPeriod" value="${note.treatmentPeriod}"></td>
            </tr>
            <tr>
                <th>진료 소견</th>
                <td id="finding">${note.finding}</td>
            </tr>
            <tr>
                <th>기타 의견</th>
                <td id="comments">${note.comments}</td>
            </tr>
            <tr>
                <th>향후 치료 의견</th>
                <td id="futureComment">${note.futureComment}</td>
            </tr>
            <tr>
                <th>용도</th>
                <td><input type="text" id="usage" value="${note.usage}"></td>
            </tr>
            <tr>
                <th>작성 날짜</th>
                <td id="modDate">${note.modDate}</td>
            </tr>
        </table>
        <button type="button" id="saveBtn" onclick="saveNote()">저장</button>
        <button type="button" id="downloadBtn" onclick="downloadPDF(${note.studykey})">PDF 다운로드</button>
    </div>
</form>
</body>
</html>
