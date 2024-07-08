<%@ page import="com.medison.mysql.user.dto.UserResponseDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../module/header.jsp"></jsp:include>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/note.css">
    <script src="${pageContext.request.contextPath}/script/note.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js"></script>
</head>
<body>
<div class="container" id="noteContent">
    <h1 class="title">소견서</h1>
    <input type="hidden" id="studykey" value="${demoNote.studykey}"/> <!-- studykey 입력 필드 추가 -->
    <input type="hidden" id="patientCode" value="${demoNote.patientCode}"> <!-- patientCode 입력 필드 추가 -->
    <input type="hidden" id="finalDoctor" value="${demoNote.finalDoctor}"> <!-- finalDoctor 입력 필드 추가 -->
    <input type="hidden" id="treatmentPeriod" value="${demoNote.treatmentPeriod}"> <!-- treatmentPeriod 입력 필드 추가 -->
    <input type="hidden" id="noteStatus" value="${demoNote.status}">
    <input type="hidden" id="currentUserId" value="<%= userId %>"> <!-- 현재 사용자 ID 입력 필드 추가 -->

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
            <td colspan="3"><input class="value" type="text" value="${demoNote.disease}" /></td>
        </tr>
        <tr>
            <td class="label">5. 진료 기간</td>
            <td class="value" colspan="3" id="period">
                ${demoNote.firstDate}&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;${demoNote.lastDate}&nbsp;&nbsp;&nbsp;(총&nbsp;&nbsp;&nbsp;${demoNote.treatmentPeriod}일간)</td>
        </tr>
        <tr class="size-up">
            <td class="label">6. 병력 및 신체검사 소견</td>
            <td colspan="3" class="value">
                <textarea class="wide-textarea" id="diseaseHistory">${demoNote.diseaseHistory}</textarea>
            </td>
        </tr>
        <tr class="size-up">
            <td class="label">7. 검사 소견</td>
            <td colspan="3" class="value">
                <textarea class="wide-textarea" id="finding">${demoNote.finding}</textarea>
            </td>
        </tr>
        <tr class="size-up">
            <td class="label">8. 진료의사 의견</td>
            <td colspan="3" class="value">
                <textarea class="wide-textarea" id="doctorComment">${demoNote.doctorComment}</textarea>
            </td>
        </tr>
        <tr class="size-up">
            <td class="label">9. 향후 치료 의견</td>
            <td colspan="3" class="value">
                <textarea class="wide-textarea" id="futureComment">${demoNote.futureComment}</textarea></td>
        </tr>
        <tr class="size-up">
            <td class="label">10. 비고</td>
            <td colspan="3" class="value">
                <textarea class="wide-textarea" id="remark">${demoNote.remark}</textarea></td>
        </tr>
    </table>
</div>
<div class="btn-container">
    <button id="saveButton" onclick="confirmSave()">저장</button>
    <button id="saveTempButton" onclick="saveTemporaryNote()">임시저장</button>
    <button id="printButton" onclick="MovePrintNote()">출력</button>
    <button onclick="cancel()">취소</button>
</div>
</body>
</html>
