<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medison</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
        $(function() {
            $("#startDate, #endDate").datepicker();
        });
    </script>
    <script src="${pageContext.request.contextPath}/script/patient.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>
</head>
<body>
<header>
    <h1 style="color: white;">Medison</h1>
</header>
<main>
    <section class="search-section">
        <form id="searchForm" action="/main" method="get">
            <input type="text" name="patientCode" id="patient-id" placeholder="환자 아이디">
            <input type="text" name="patientName" id="patient-name" placeholder="환자 이름">
            <select name="modality" id="test-equipment" required>
                <option value="" disabled selected>검사 장비</option>
                <option value="CR">CR</option>
                <option value="CT">CT</option>
                <option value="DR">DR</option>
                <option value="ES">ES</option>
                <option value="MR">MR</option>
                <option value="SC">SC</option>
                <option value="US">US</option>
                <option value="XA">XA</option>
            </select>

            <select name="reportStatus" id="test-status" required>
                <option value="" disabled selected>판독 상태</option>
                <option value="3">읽지않음</option>
                <option value="5">예비판독</option>
                <option value="6">판독완료</option>
            </select>

            <select name="examStatus" id="verify" required>
                <option value="" disabled selected>Verify</option>
                <option value="1">Y</option>
                <option value="0">N</option>
            </select>
            <input type="text" name="startDate" id="startDate" placeholder="시작 날짜">
            <input type="text" name="endDate" id="endDate" placeholder="종료 날짜">
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
                <tr class="clickable" onclick="showPatientDetails('${study.pid}'); showReportDetails('${study.studykey}')">
                    <td>${study.pid}</td>
                    <td>${study.pname}</td>
                    <td>${study.modality}</td>
                    <td>${study.studydesc}</td>
                    <td>${study.studydate}</td>
                    <td><c:choose>
                        <c:when test="${study.reportstatus == 3}">읽지않음</c:when>
                        <c:when test="${study.reportstatus == 5}">예비판독</c:when>
                        <c:when test="${study.reportstatus == 6}">판독완료</c:when>
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
            <button onclick="location.href='/main?page=${currentPage + 1}'" <c:if test="${currentPage + 1 >= totalPages}">disabled</c:if>>&gt;</button>
        </div>
    </section>
    <section class="details-container">
        <section id="patient-details" class="detail-section">
            <h2>환자 정보</h2>
            <table>
                <tr><th>코드</th><td id="patient-code"></td></tr>
                <tr><th>이름</th><td id="patient-name"></td></tr>
                <tr><th>성별</th><td id="patient-sex"></td></tr>
                <tr><th>생년월일</th><td id="patient-birth"></td></tr>
                <tr><th>흡연</th><td>
                    <select id="patient-smoking">
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </td></tr>
                <tr><th>음주</th><td>
                    <select id="patient-drinking">
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                    </select>
                </td></tr>
                <tr><th>의료 이력</th><td><input type="text" id="patient-history"></td></tr>
                <tr><th>주의 사항</th><td><input type="text" id="patient-caution"></td></tr>
            </table>
            <button id="save-button" style="display: none;" onclick="savePatientDetails()">저장</button>
        </section>
        <section id="report-details" class="detail-section">
            <h2>검사 리포트</h2>
            <table>
                <tr><th>예비판독의</th><td id="preDoctor"></td></tr>
                <tr><th>판독의</th><td id="doctor"></td></tr>
                <tr><th>최종 판독의</th><td id="finalDoctor"></td></tr>
                <tr><th>환자코드</th><td id="report-patientCode"></td></tr>
                <tr><th>판독 상태</th><td id="status"></td></tr>
                <tr><th>코멘트</th><td><input type="text" id="comments"></td></tr>
                <tr><th>검사소견</th><td><input type="text" id="finding"></td></tr>
                <tr><th>향후 치료 의견</th><td><input type="text" id="futureComment"></td></tr>
            </table>
            <button id="preliminary-button" style="display: none;" onclick="savePreliminaryReport()">예비판독</button>
            <button id="final-button" style="display: none;" onclick="saveFinalReport()">최종판독</button>
        </section>
    </section>
</main>
</body>
</html>
