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

        $(function() {
            $("#calendar").datepicker({
                numberOfMonths: 1,
                onSelect: function(dateText, inst) {
                    if (!this.startDate) {
                        this.startDate = dateText;
                        $("#startDate").val(dateText);
                    } else if (!this.endDate) {
                        this.endDate = dateText;
                        $("#endDate").val(dateText);
                        this.startDate = null;
                        this.endDate = null;
                    }
                }
            });

            $("#startDate, #endDate").datepicker({
                dateFormat: "yy-mm-dd"
            });

            $("#searchForm").submit(function(event) {
                const startDate = $("#startDate").datepicker("getDate");
                const endDate = $("#endDate").datepicker("getDate");

                if (startDate && endDate) {
                    const formattedStartDate = $.datepicker.formatDate("yy-mm-dd", startDate);
                    const formattedEndDate = $.datepicker.formatDate("yy-mm-dd", endDate);

                    $("#startDate").val(formattedStartDate);
                    $("#endDate").val(formattedEndDate);
                }
            });

            document.getElementById('userName').textContent = userName;
            document.getElementById('userPosition').textContent = translatePosition(userPosition);

            $(document).on('click', '.bookmark-btn', function(event) {
                event.stopPropagation();
                var button = $(this);
                var studyKey = button.data('study-key');
                var bookmarked = button.data('bookmarked');
                var code = button.data('code');

                $.ajax({
                    type: 'POST',
                    url: bookmarked ? '/api/bookmarks/studies/' + studyKey + '/unfavorite' : '/api/bookmarks/studies/' + studyKey,
                    data: { code: code },
                    success: function(response) {
                        if (bookmarked) {
                            button.data('bookmarked', false).text('☆').removeClass('bookmarked').addClass('not-bookmarked');
                        } else {
                            button.data('bookmarked', true).text('⭐').removeClass('not-bookmarked').addClass('bookmarked');
                        }
                    },
                    error: function(error) {
                        console.log('Error:', error);
                    }
                });
            });
        });
    </script>
    <script src="${pageContext.request.contextPath}/script/patient.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>
</head>
<body>
<main>
    <div class="sidebar">
        <form id="searchForm" action="/main" method="get">
            <div class="calendar" id="calendar"></div>
            <label for="startDate">시작 날짜:</label>
            <input type="text" id="startDate" name="startDate" placeholder="시작 날짜">
            <label for="endDate">종료 날짜:</label>
            <input type="text" id="endDate" name="endDate" placeholder="종료 날짜">
            <button type="submit">검색</button>
        </form>

        <div class="member-info">
            <img src="${pageContext.request.contextPath}/image/profile.png" class="profile-image">
            <p class="member-id"><%= userId %></p>
            <p class="member-name"><span id="userName"> </span><span id="userPosition"></span></p>
            <button class="info-update">정보수정</button>
        </div>
    </div>
    <div class="main-content">
        <section class="search-section">
            <form id="searchForm" action="/main" method="get">
                <input type="text" name="patientCode" id="patient-id" placeholder="환자 아이디">
                <input type="text" name="patientName" id="patientName" placeholder="환자 이름">
                <select name="modality" id="test-equipment">
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

                <select name="reportStatus" id="test-status">
                    <option value="" disabled selected>판독 상태</option>
                    <option value="3">읽지않음</option>
                    <option value="5">예비판독</option>
                    <option value="6">판독완료</option>
                </select>

                <select name="examStatus" id="verify">
                    <option value="" disabled selected>Verify</option>
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
                    <c:forEach var="item" items="${studies}">
                    <c:set var="study" value="${item.study}" />
                    <c:set var="bookmark" value="${item.bookmark}" />
                    <c:set var="status" value="${item.status}" />
                <tr class="clickable" onclick="showPatientDetails('${study.pid}'); showReportDetails('${study.studykey}')">
                <td>
                <button class="bookmark-btn ${bookmark != null ? 'bookmarked' : 'not-bookmarked'}"
        id="bookmarkButton${study.studykey}"
        data-study-key="${study.studykey}"
        data-code="${bookmark != null ? bookmark.code : 'null'}"
        data-bookmarked="${bookmark != null}">
    ${bookmark != null ? '⭐' : '☆'}
</button>

                    </td>
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
            </tr>
            </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <c:if test="${currentPage > 0}">
                    <button onclick="location.href='/main?page=${currentPage - 1}&patientCode=${param.patientCode}&patientName=${param.patientName}&modality=${param.modality}&reportStatus=${param.reportStatus}&examStatus=${param.examStatus}&startDate=${param.startDate}&endDate=${param.endDate}'">&lt;</button>
                </c:if>
                <span>${currentPage + 1}</span> / <span>${totalPages}</span>
                <c:if test="${currentPage + 1 < totalPages}">
                    <button onclick="location.href='/main?page=${currentPage + 1}&patientCode=${param.patientCode}&patientName=${param.patientName}&modality=${param.modality}&reportStatus=${param.reportStatus}&examStatus=${param.examStatus}&startDate=${param.startDate}&endDate=${param.endDate}'">&gt;</button>
                </c:if>
            </div>
            <div class="button-container">
                <button onclick="location.href='/bookmark/myList?userId=<%= userId %>'">북마크 목록 보기</button>
                <button class="emergency-button" onclick="location.href='/emergency'">긴급관리</button>
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
