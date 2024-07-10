<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 2024-07-02
  Time: 오전 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./module/header.jsp"></jsp:include>
<html>
<head>
</head>
<body>
<c:if test="${emergency gt 0}">
    <script>
        const result = confirm(`현재 긴급사항이 ${emergency}개 존재합니다. 확인하시겠습니까?`);
<%--        <%--%>
<%--            session.removeAttribute("emergency");--%>
<%--        %>--%>
        if (result) {
            location.href = '/emergency';
        }else{
            location.href = '/main';
        }
    </script>
</c:if>
</body>
</html>
