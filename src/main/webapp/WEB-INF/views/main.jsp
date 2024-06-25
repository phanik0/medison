<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medison</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <script>
        function showPatientDetails(pid) {
            fetch(`/patient?pid=${pid}`)
                .then(response => response.json())
                .then(data => {
                    const patientDetails = document.getElementById('patient-details');
                    patientDetails.innerHTML = `
                        <h2>환자 정보</h2>
                        <table>
                            <tr><th>코드</th><td>${data.code}</td></tr>
                            <tr><th>이름</th><td>${data.name}</td></tr>
                            <tr><th>성별</th><td>${data.sex}</td></tr>
                            <tr><th>생년월일</th><td>${data.birth}</td></tr>
                            <tr><th>흡연</th><td>${data.smoking ? 'Yes' : 'No'}</td></tr>
                            <tr><th>음주</th><td>${data.drinking ? 'Yes' : 'No'}</td></tr>
                            <tr><th>의료 이력</th><td>${data.medicalHistory}</td></tr>
                            <tr><th>주의 사항</th><td>${data.caution}</td></tr>
                            <tr><th>등록일</th><td>${data.regDate}</td></tr>
                            <tr><th>수정일</th><td>${data.modDate}</td></tr>
                        </table>`;
                });
        }
    </script>
</head>
<body>
<header>
    <h1 style="color: #0056b3;">Medison</h1>
</header>
<main>
    <section class="search-section">
        <form id="searchForm" action="/main" method="get">
            <input type="text" name="patientCode" id="patient-id" placeholder="환자 아이디">
            <input type="text" name="patientName" id="patient-name" placeholder="환자 이름">
            <select name="modality" id="test-equipment">
                <option value="">검사 장비</option>
                <option value="CT">CT</option>
                <option value="MRI">MRI</option>
            </select>
            <select name="reportStatus" id="test-status">
                <option value="">판독 상태</option>
                <option value="1">판독완료</option>
                <option value="0">판독대기</option>
            </select>
            <select name="examStatus" id="verify">
                <option value="">Verify</option>
                <option value="1">Y</option>
                <option value="0">N</option>
            </select>
            <button type="submit">검색</button>
        </form>
    </section>
    <section class="result-section">
        <table>
            <thead>
            <tr>
                <th>환자 코드</th>
                <th>환자 이름</th>
                <th>검사 장비</th>
                <th>검사 부위</th>
                <th>검사 날짜</th>
                <th>판독 상태</th>
                <th>Verify</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="study" items="${studies}">
                <tr onclick="showPatientDetails('${study.pid}')">
                    <td>${study.pid}</td>
                    <td>${study.pname}</td>
                    <td>${study.modality}</td>
                    <td>${study.studydesc}</td>
                    <td>${study.studydate}</td>
                    <td><c:choose>
                        <c:when test="${study.reportstatus == 1}">판독완료</c:when>
                        <c:otherwise>판독대기</c:otherwise>
                    </c:choose></td>
                    <td><c:choose>
                        <c:when test="${study.examstatus == 1}">Y</c:when>
                        <c:otherwise>N</c:otherwise>
                    </c:choose></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="pagination">
            <button onclick="location.href='/main?page=${currentPage - 1}'" <c:if test="${currentPage == 0}">disabled</c:if>>&lt;</button>
            <span>${currentPage + 1}</span> / <span>${totalPages}</span>
            <button onclick="location.href='/main?page=${currentPage + 1}'"
