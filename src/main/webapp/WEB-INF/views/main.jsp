<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Study List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<div class="container">
    <h1>Study List</h1>

    <table class="table table-horizontal table-bordered">
        <thead class="thead-strong">
        <tr>
            <th>스터디키</th>
            <th>환자아이디</th>
            <th>환자 이름</th>
            <th>검사 장비</th>
            <th>검사어쩌구</th>
            <th>검사 날짜</th>
            <th>검사 여부</th>
            <th>시리즈 개수</th>
            <th>이미지 개수</th>
            <th>verity</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="study" items="${studies}">
            <tr>
                <td><c:out value="${study.studykey}" /></td>
                <td><c:out value="${study.pid}" /></td>
                <td><c:out value="${study.pname}" /></td>
                <td><c:out value="${study.modality}" /></td>
                <td><c:out value="${study.studydesc}" /></td>
                <td><c:out value="${study.studydate}" /></td>
                <td><c:out value="${study.reportstatus}" /></td>
                <td><c:out value="${study.seriescnt}" /></td>
                <td><c:out value="${study.imagecnt}" /></td>
                <td><c:out value="${study.examstatus}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:if test="${currentPage > 0}">
            <a href="<c:url value='/main/${currentPage - 1}' />">Previous</a>
        </c:if>
        <span>${currentPage + 1}</span>
        <c:if test="${currentPage < totalPages - 1}">
            <a href="<c:url value='/main/${currentPage + 1}' />">Next</a>
        </c:if>
    </div>
</div>
</body>
</html>
