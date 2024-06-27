<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Medison</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/note.js"></script>
</head>
<body>
<h1>소견서</h1>
<section id="note">
    <table>
        <tr>
            <th>코드</th>
            <td id="code">${note.code}</td>
        </tr>
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
            <td id="disease">${note.disease}</td>
        </tr>
        <tr>
            <th>치료 기간</th>
            <td id="treatmentPeriod">${note.treatmentPeriod}</td>
        </tr>
        <tr>
            <th>소견</th>
            <td id="finding">${note.finding}</td>
        </tr>
        <tr>
            <th>소견</th>
            <td id="comments">${note.comments}</td>
        </tr>
        <tr>
            <th>향후 소견</th>
            <td id="futureComment">${note.futureComment}</td>
        </tr>
        <tr>
            <th>용도</th>
            <td id="usage">${note.usage}</td>
        </tr>
        <tr>
            <th>등록일</th>
            <td id="regDate">${note.regDate}</td>
        </tr>
        <tr>
            <th>수정일</th>
            <td id="modDate">${note.modDate}</td>
        </tr>
    </table>
    <button onclick="downloadPDF(${note.studykey})">PDF 다운로드</button>
</section>

</body>
</html>
