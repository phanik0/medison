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
    String userName = user.getName();
    String userPosition = user.getPosition();
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/note.css">
    <script src="${pageContext.request.contextPath}/js/showNote.js"></script>
    <script>
        var userName = '<%= userName %>';
        var userPosition = '<%= userPosition %>';

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
<div class="container">
    <h1 class="title">소견서</h1>
    <table class="info-table">
        <tr>
            <td class="label">1. 성명</td>
            <td class="value">${demoNote.pName}</td>
            <td class="label">2. 생년월일</td>
            <td class="value">${demoNote.pBirth}</td>
        </tr>
        <tr>
            <td class="label">4. 병명</td>
            <td colspan="3" class="value" style="padding: 0 5px 0 8px"><input style="width: 98%;border: none;font-size: 100%;" type="text" id="disease"></td>
        </tr>
        <tr>
            <td class="label">5. 진료 기간</td>
            <td class="value" colspan="3">${demoNote.firstDate}&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;${demoNote.lastDate}&nbsp;&nbsp;&nbsp;(총&nbsp;&nbsp;&nbsp;${demoNote.treatmentPeriod}일간)</td>
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
            <td class="label">10. 용도</td>
            <td class="value">
                <select class="select-large">
                    <option value="보험">보험</option>
                    <option value="공단">공단</option>
                    <option value="주민센터">주민센터</option>
                </select>
            </td>
            <td class="label">11. 비고</td>
            <td class="value">비고</td>
        </tr>
    </table>
    <div class="signature">
        <div class="row">
            <div class="label">담당의사 면허번호</div>
            <div class="value"><span id="userId"></span></div>
        </div>
        <div class="row">
            <div class="label">성명</div>
            <div class="value"><span id="userName"> </span><span id="userPosition"></span></div>
        </div>
        <div class="row">
            <div class="label">의료기관 주소</div>
            <div class="value">서울특별시 종로구 대학로 101</div>
        </div>
    </div>
    <div class="footer">
        <div class="hospital-name">메가병원장</div>
        <div class="stamp"><img src="${pageContext.request.contextPath}/image/seal.png" alt="seal"></div>
    </div>
    <div class="note">※ 본서에 본원의 직인이 없으면 무효임.</div>

    <div>
        <button>저장</button>
        <button>취소</button>
    </div>
</div>
</body>
</html>
