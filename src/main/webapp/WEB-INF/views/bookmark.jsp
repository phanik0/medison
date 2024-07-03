<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.medison.mysql.user.dto.UserResponseDto" %>

<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/user/login");
        return;
    }

    UserResponseDto user = (UserResponseDto) userSession.getAttribute("user");
    String userId = user.getId();
    String userName = user.getName();
    String userPosition = user.getPosition();
    int userDepartmentCode = user.getDepartmentCode();
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="module/header.jsp" %>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bookmark.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/bookmark.js"></script>
    <script src="${pageContext.request.contextPath}/script/patient.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>
    <script>
        var userId = '<%= userId %>';
        var userName = '<%= userName %>';
        var userPosition = '<%= userPosition %>';
        var userDepartmentCode = <%= userDepartmentCode %>;

        function translatePosition(position) {
            switch(position) {
                case 'professor':
                    return '교수';
                case 'intern':
                    return '인턴';
                case 'fellow':
                    return '펠로우';
                case 'resident':
                    return '레지던트';
                default:
                    return position;
            }
        }
    </script>
</head>
<body>
<main>
    <div class="main-content">
        <h2>북마크 목록</h2>
        <section class="result-section">
            <table>
                <thead>
                    <tr>
                        <th>북마크</th>
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
                    <c:forEach var="item" items="${bookmarkWithStudies}">
                        <c:set var="study" value="${item.study}" />
                        <c:set var="bookmark" value="${item.bookmark}" />
                        <tr class="clickable" onclick="showPatientDetails('${study.pid}'); showReportDetails('${study.studykey}')">
                            <td>
                                <button class="delete-bookmark-btn" data-study-key="${study.studykey}" data-user-id="${userId}">삭제</button>
                            </td>
                            <td>${study.pid}</td>
                            <td>${study.pname}</td>
                            <td>${study.modality}</td>
                            <td>${study.studydesc}</td>
                            <td>${study.studydate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${study.reportstatus == 3}">읽지않음</c:when>
                                    <c:when test="${study.reportstatus == 5}">예비판독</c:when>
                                    <c:when test="${study.reportstatus == 6}">판독완료</c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${study.examstatus == 1}">Y</c:when>
                                    <c:otherwise>N</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <c:if test="${currentPage > 0}">
                    <button onclick="location.href='/bookmark/myList?userId=${userId}&page=${currentPage - 1}'">&lt;</button>
                </c:if>
                <span>${currentPage + 1}</span> / <span>${totalPages}</span>
                <c:if test="${currentPage + 1 < totalPages}">
                    <button onclick="location.href='/bookmark/myList?userId=${userId}&page=${currentPage + 1}'">&gt;</button>
                </c:if>
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
                <div class="button-container">
                    <button id="preliminary-button" style="display: none;" onclick="savePreliminaryReport()">예비판독</button>
                    <button id="final-button" style="display: none;" onclick="saveFinalReport()">최종판독</button>
                </div>
            </section>
        </section>
        <button onclick="location.href='/main'">메인으로 돌아가기</button>
    </div>
</main>
</body>
</html>
