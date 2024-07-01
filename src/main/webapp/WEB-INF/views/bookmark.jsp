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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/bookmark.js"></script>
    <script>
        // 사용자 정보를 전역 변수에 저장
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
            <section class="bookmark-section">
                <h2>북마크 목록</h2>
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
                        <th>코멘트</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bookmark" items="${bookmarks}" varStatus="status">
                        <c:set var="study" value="${studies[status.index]}" />
                        <tr>
                            <td>${study.pid}</td>
                            <td>${study.pname}</td>
                            <td>${study.modality}</td>
                            <td>${study.studydesc}</td>
                            <td>${study.studydate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${status == 3}">읽지않음</c:when>
                                    <c:when test="${status == 5}">예비판독</c:when>
                                    <c:when test="${status == 6}">판독완료</c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${study.examstatus == 1}">Y</c:when>
                                    <c:otherwise>N</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${bookmark.comments}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </section>
        <section class="details-container">

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
    </div>
</main>

<!-- 북마크 모달 -->
<div id="bookmarkModal" title="북마크 추가">
    <form>
        <input type="hidden" id="bookmarkStudyKey">
        <label for="bookmarkComments">코멘트:</label>
        <textarea id="bookmarkComments" rows="4" cols="50"></textarea>
    </form>
</div>

<%@ include file="module/footer.jsp" %>
</body>
</html>
