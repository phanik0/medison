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
</head>
<%@ include file="../module/header.jsp" %>
<body>
<table>
    <thead>
    <tr>
        <td>인덱스</td>
        <td>검사 키</td>
        <td>열람일자</td>
        <td>이름</td>
        <td>연락처</td>
        <td>생년월일</td>
        <td>거주지</td>
        <td>부서</td>
        <td>직급</td>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty readLog}">
        <c:forEach var="log" items="${readLog}">
            <tr>
                <td>${log.logCode}</td>
                <td>${log.studykey}</td>
                <td>${log.regDate}</td>
                <td>${log.userName}</td>
                <td>${log.userPhone}</td>
                <td>${log.userBirth}</td>
                <td>${log.userAddress}</td>
                <td>${log.department}</td>
                <td>${log.position}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>
