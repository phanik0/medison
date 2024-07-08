<%--
  Created by IntelliJ IDEA.
  User: 김승우
  Date: 2024-07-01
  Time: 오후 3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="./module/header.jsp"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/emergency.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/script/patient.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>
</head>
<body>
<div class="main-content">
       <span style="font-size: 40px; margin-bottom: 20px"><img style="width: 50px" src="https://cdn-icons-png.flaticon.com/128/4329/4329979.png"> 긴급 관리</span>
        <section class="result-section">
            <table>
                <thead>
                <tr>
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
                <c:forEach var="item" items="${emergencies}">
                    <c:set var="study" value="${item['study']}"/>
                    <c:set var="status" value="${item['status']}"/>
                    <tr class="clickable"
                        onclick="showPatientDetails('${study.pid}'); showReportDetails('${study.studykey}')" ondblclick="location.href='/study/${study.studykey}'">
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
        </section>
        <section class="details-container">
            <section id="patient-details" class="detail-section">
                <h2>환자 정보</h2>
                <table>
                    <tr>
                        <th>코드</th>
                        <td id="patient-code"></td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td id="patient-name"></td>
                    </tr>
                    <tr>
                        <th>성별</th>
                        <td id="patient-sex"></td>
                    </tr>
                    <tr>
                        <th>생년월일</th>
                        <td id="patient-birth"></td>
                    </tr>
                    <tr>
                        <th>흡연</th>
                        <td>
                            <select id="patient-smoking">
                                <option value="true">Yes</option>
                                <option value="false">No</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>음주</th>
                        <td>
                            <select id="patient-drinking">
                                <option value="true">Yes</option>
                                <option value="false">No</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>의료 이력</th>
                        <td><input type="text" id="patient-history"></td>
                    </tr>
                    <tr>
                        <th>주의 사항</th>
                        <td><input type="text" id="patient-caution"></td>
                    </tr>
                </table>
                <button id="save-button" style="display: none;" onclick="savePatientDetails()">저장</button>
            </section>
            <section id="report-details" class="detail-section">
                <h2>검사 리포트</h2>
                <table>
                    <tr>
                        <th>예비판독의</th>
                        <td id="preDoctor"></td>
                    </tr>
                    <tr>
                        <th>판독의</th>
                        <td id="doctor"></td>
                    </tr>
                    <tr>
                        <th>최종 판독의</th>
                        <td id="finalDoctor"></td>
                    </tr>
                    <tr>
                        <th>환자코드</th>
                        <td id="report-patientCode"></td>
                    </tr>
                    <tr>
                        <th>판독 상태</th>
                        <td id="status"></td>
                    </tr>
                    <tr>
                        <th>코멘트</th>
                        <td><input type="text" id="comments"></td>
                    </tr>
                    <tr>
                        <th>검사소견</th>
                        <td><input type="text" id="finding"></td>
                    </tr>
                    <tr>
                        <th>향후 치료 의견</th>
                        <td><input type="text" id="futureComment"></td>
                    </tr>
                </table>
                <div class="button-container">
                    <button id="preliminary-button" style="display: none;" onclick="savePreliminaryReport()">예비판독</button>
                    <button id="final-button" style="display: none;" onclick="saveFinalReport()">최종판독</button>
                </div>
            </section>
        </section>
</div>
<%@ include file="module/footer.jsp" %>
</body>
</html>
