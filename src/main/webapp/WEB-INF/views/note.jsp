<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>소견서</h1>
<section id="note">
    <table>
        <tr>
            <th>코드</th>
            <td>${note.code}</td>
        </tr>
        <tr>
            <th>연구키</th>
            <td>${note.studykey}</td>
        </tr>
        <tr>
            <th>최종 담당 의사</th>
            <td>${note.finalDoctor}</td>
        </tr>
        <tr>
            <th>환자 코드</th>
            <td>${note.patientCode}</td>
        </tr>
        <tr>
            <th>질병</th>
            <td>${note.disease}</td>
        </tr>
        <tr>
            <th>치료 기간</th>
            <td>${note.treatmentPeriod}</td>
        </tr>
        <tr>
            <th>소견</th>
            <td>${note.finding}</td>
        </tr>
        <tr>
            <th>의견</th>
            <td>${note.comments}</td>
        </tr>
        <tr>
            <th>향후 의견</th>
            <td>${note.futureComment}</td>
        </tr>
        <tr>
            <th>용도</th>
            <td>${note.usage}</td>
        </tr>
        <tr>
            <th>등록일</th>
            <td>${note.regDate}</td>
        </tr>
        <tr>
            <th>수정일</th>
            <td>${note.modDate}</td>
        </tr>
    </table>
</section>

</body>
</html>
