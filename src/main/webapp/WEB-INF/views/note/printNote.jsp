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

</head>
<body>
<div class="content-container">
    <div class="container" id="noteContent">
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
                <td class="label">11. 비고</td>
                <td colspan="3" class="value">${demoNote.remark}</td>
            </tr>
        </table>
        <div class="signature">
            <div class="row">
                <div class="label">담당의사 면허번호</div>
                <div class="value"><span id="userId">${userId}</span></div>
            </div>
            <div class="row">
                <div class="label">성명</div>
                <div class="value" id="finalDoctorName">${demoNote.finalDoctorName}</div>
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
    </div>
</div>
<script>
    function printNote() {
        const noteContent = document.getElementById('noteContent');

        html2canvas(noteContent, {
            useCORS: true,
            scale: 2
        }).then(canvas => {
            const imgData = canvas.toDataURL('image/png');
            const pdf = new jsPDF({
                orientation: 'portrait',
                unit: 'mm',
                format: 'a4'
            });
            const imgWidth = 210; // A4 width in mm
            const pageHeight = 297; // A4 height in mm
            const imgHeight = canvas.height * imgWidth / canvas.width;

            // If the image height exceeds the page height, scale it down
            const scale = Math.min(1, pageHeight / imgHeight);
            pdf.addImage(imgData, 'PNG', 0, 0, imgWidth * scale, imgHeight * scale);

            pdf.save('note.pdf');
        }).catch(error => {
            console.error('Error capturing the note content:', error);
        });
    }
    // Call the print function when the page loads
    window.onload = function() {
        printNote();
    }
</script>
</body>
</html>
