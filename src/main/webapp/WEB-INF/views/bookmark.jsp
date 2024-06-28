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
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="module/header.jsp" %>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<main>
    <div class="sidebar">
        <!-- 사이드바 내용 -->
    </div>
    <div class="main-content">
        <section class="bookmark-section">
            <h2>북마크 목록</h2>
            <table>
                <thead>
                <tr>
                    <th>환자 코드</th>
                    <th>환자 이름</th>
                    <th>검사 장비</th>
                    <th>검사 날짜</th>
                    <th>코멘트</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="bookmark" items="${bookmarks}">
                    <tr>
                        <td>${bookmark.study.pid}</td>
                        <td>${bookmark.study.pname}</td>
                        <td>${bookmark.study.modality}</td>
                        <td>${bookmark.study.studydate}</td>
                        <td>${bookmark.comments}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </div>
</main>
<%@ include file="module/footer.jsp" %>
</body>
</html>
