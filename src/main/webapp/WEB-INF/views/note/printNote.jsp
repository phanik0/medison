<%@ page import="com.medison.mysql.user.dto.UserResponseDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/printNote.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js"></script>
    <script src="${pageContext.request.contextPath}/script/print-note.js"></script>
</head>
<body>
<div class="content-container">
    <div class="container" id="noteContent">
        <h1 class="title">소견서</h1>
        <table class="info-table">
            <tr>
                <td class="label">1. 성명</td>
                <td class="value">${demoNote.pName}</td>
                <td class="label">2. 성별</td>
                <td class="value">
                    <c:choose>
                        <c:when test="${demoNote.pSex == 'F'}">여성</c:when>
                        <c:otherwise>남성</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="label">3. 생년월일</td>
                <td colspan="3" class="value">${demoNote.pBirth}</td>
            </tr>
            <tr>
                <td class="label">4. 병명</td>
                <td colspan="3" class="value">${demoNote.disease}</td>
            </tr>
            <tr>
                <td class="label">5. 진료 기간</td>
                <td class="value" colspan="3" id="period">
                    ${demoNote.firstDate}&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;${demoNote.lastDate}&nbsp;&nbsp;&nbsp;(총&nbsp;&nbsp;&nbsp;${demoNote.treatmentPeriod}일간)</td>
            </tr>
            <tr class="size-up">
                <td class="label">6. 병력 및 신체검사 소견</td>
                <td colspan="3" class="value">${demoNote.diseaseHistory}</td>
            </tr>
            <tr class="size-up">
                <td class="label">7. 검사 소견</td>
                <td colspan="3" class="value">${demoNote.finding}</td>
            </tr>
            <tr class="size-up">
                <td class="label">8. 진료의사 의견</td>
                <td colspan="3" class="value">${demoNote.doctorComment}</td>
            </tr>
            <tr class="size-up">
                <td class="label">9. 향후 치료 의견</td>
                <td colspan="3" class="value">${demoNote.futureComment}</td>
            </tr>
            <tr class="size-up">
                <td class="label">10. 비고</td>
                <td colspan="3" class="value">${demoNote.remark}</td>
            </tr>
        </table>
        <div class="signature">
            <div class="row">
                <div class="label">성명</div>
                <div class="value" id="finalDoctorName">${demoNote.finalDoctorName}</div>
            </div>
            <div class="row">
                <div class="label">의료기관 주소</div>
                <div class="value">서울특별시 강남구 역삼동</div>
            </div>
        </div>
        <div class="footer">
            <div class="hospital-name">메가병원장</div>
            <div class="stamp"><img src="${pageContext.request.contextPath}/image/seal.png" alt="seal"></div>
        </div>
        <div class="note">※ 본서에 본원의 직인이 없으면 무효임.</div>
    </div>
</div>
</body>
</html>
