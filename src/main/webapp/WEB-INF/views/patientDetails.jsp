<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>환자 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<header>
    <h1 style="color: #0056b3;">환자 정보</h1>
</header>
<main>
    <section id="patient-details" class="detail-section">
        <h2>환자 정보</h2>
        <table>
            <tr><th>코드</th><td>${patient.code}</td></tr>
            <tr><th>이름</th><td>${patient.name}</td></tr>
            <tr><th>성별</th><td>${patient.sex}</td></tr>
            <tr><th>생년월일</th><td>${patient.birth}</td></tr>
            <tr><th>흡연</th><td>${patient.smoking ? 'Yes' : 'No'}</td></tr>
            <tr><th>음주</th><td>${patient.drinking ? 'Yes' : 'No'}</td></tr>
            <tr><th>의료 이력</th><td>${patient.medicalHistory}</td></tr>
            <tr><th>주의 사항</th><td>${patient.caution}</td></tr>
            <tr><th>등록일</th><td>${patient.regDate}</td></tr>
            <tr><th>수정일</th><td>${patient.modDate}</td></tr>
        </table>
    </section>
</main>
</body>
</html>