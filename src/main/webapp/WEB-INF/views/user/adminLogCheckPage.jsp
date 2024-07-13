<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: 김승우--%>
<%--  Date: 24. 7. 11.--%>
<%--  Time: 오후 5:21--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/logCheck.css">--%>
<%--</head>--%>
<%--<%@ include file="../module/header.jsp" %>--%>
<%--<body>--%>
<%--<div id="main-content">--%>
<%--    <span style="font-size: 40px; margin-bottom: 20px"><img style="width: 50px"--%>
<%--                                                            src="https://cdn-icons-png.flaticon.com/128/684/684831.png"> 로그 확인</span>--%>
<%--    <section id="table-section">--%>
<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>인덱스</th>--%>
<%--                <th>검사 키</th>--%>
<%--                <th>열람일자</th>--%>
<%--                <th>이름</th>--%>
<%--                <th>연락처</th>--%>
<%--                <th>생년월일</th>--%>
<%--                <th>거주지</th>--%>
<%--                <th>부서</th>--%>
<%--                <th>직급</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:if test="${not empty readLog}">--%>
<%--                <c:forEach var="log" items="${readLog}" varStatus="i">--%>
<%--                    <tr>--%>
<%--                        <td>${i.count}</td>--%>
<%--                        <td>${log.studykey}</td>--%>
<%--                        <td>${log.regDate}</td>--%>
<%--                        <td>${log.userName}</td>--%>
<%--                        <td>${log.userPhone}</td>--%>
<%--                        <td>${log.userBirth}</td>--%>
<%--                        <td>${log.userAddress}</td>--%>
<%--                        <td>${log.department}</td>--%>
<%--                        <td>${log.position}</td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--            </c:if>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </section>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>

<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 24. 7. 11.
  Time: 오후 5:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/logCheck.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/log-check.js"></script>
</head>
<%@ include file="../module/header.jsp" %>
<body>
<div id="main-content">
    <span style="font-size: 40px; margin-bottom: 20px"><img style="width: 50px"
                                                            src="https://cdn-icons-png.flaticon.com/128/281/281760.png"> 로그 확인</span>
    <section id="table-section">
        <table>
            <thead>
            <tr>
                <th>인덱스</th>
                <th>검사 키</th>
                <th>열람일자</th>
                <th>이름</th>
                <th>연락처</th>
                <th>생년월일</th>
                <th>거주지</th>
                <th>부서</th>
                <th>직급</th>
            </tr>
            </thead>
            <tbody id="logTableBody">
            <!-- 데이터가 여기에 삽입됩니다 -->
            </tbody>
        </table>
    </section>
    <div class="pagination" id="pagination">
        <!-- 페이지 번호가 여기에 삽입됩니다 -->
    </div>
</div>
</body>
</html>