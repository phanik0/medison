<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/study.css">
    <script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
</head>
<jsp:include page="../module/header.jsp"></jsp:include>
<body>
<c:choose>
    <c:when test="${empty user}">
        <c:redirect url="/user/login"></c:redirect>
    </c:when>
    <c:otherwise>
        <input type="hidden" id="userId" value="${user.id}">
    </c:otherwise>
</c:choose>

<div id="top-bar">
    <img class="tool-button" id="work-list" src="https://cdn-icons-png.flaticon.com/128/14203/14203876.png"
         onclick="location.href='/main'">
    <div id="pure-tool">
        <img class="tools" id="StackScroll" src="https://cdn-icons-png.flaticon.com/128/482/482824.png">
        <img class="tools" id="Zoom" src="https://cdn-icons-png.flaticon.com/128/8249/8249218.png">
        <img class="tools" id="Magnify" src="https://cdn-icons-png.flaticon.com/128/16/16492.png">
        <img class="tools" id="Pan" src="https://cdn-icons-png.flaticon.com/128/25/25476.png">
        <img class="tools" id="WindowLevel" src="https://cdn-icons-png.flaticon.com/128/12148/12148999.png">
        <img class="tools" id="PlanarRotate" src="https://cdn-icons-png.flaticon.com/128/4716/4716164.png">
    </div>
    <img id="annotation-list" class="tool-button" src="https://cdn-icons-png.flaticon.com/128/8596/8596640.png">
    <div id="annotation-tool">
        <img class="tools" id="Length" src="https://cdn-icons-png.flaticon.com/128/6644/6644036.png">
        <img class="tools" id="Angle" src="https://cdn-icons-png.flaticon.com/128/262/262070.png">
        <img class="tools" id="Height" src="https://cdn-icons-png.flaticon.com/128/3789/3789467.png">
        <img class="tools" id="Probe" src="https://cdn-icons-png.flaticon.com/128/6664/6664962.png">
        <img class="tools" id="RectangleROI" src="https://cdn-icons-png.flaticon.com/128/54/54538.png">
        <img class="tools" id="EllipticalROI" src="https://cdn-icons-png.flaticon.com/128/1598/1598853.png">
        <img class="tools" id="CircleROI" src="https://cdn-icons-png.flaticon.com/128/60/60722.png">
        <img class="tools" id="Bidirectional" src="https://cdn-icons-png.flaticon.com/128/657/657023.png">
        <img class="tools" id="CobbAngle" src="https://cdn-icons-png.flaticon.com/128/715/715800.png">
        <img class="tools" id="ArrowAnnotate" src="https://cdn-icons-png.flaticon.com/128/21/21112.png">
        <img class="tools" id="Eraser" src="https://cdn-icons-png.flaticon.com/128/15109/15109444.png">
        <img class="tools" id="PlanarFreehandROI" src="https://cdn-icons-png.flaticon.com/128/1828/1828911.png">
    </div>
    <img id="row-column-button" class="tool-button" src="https://cdn-icons-png.flaticon.com/128/3/3467.png">
    <div id="check-row-column-by-series">
        <div class="row-column-box" data-row="1" data-column="1"></div>
        <div class="row-column-box" data-row="1" data-column="2"></div>
        <div class="row-column-box" data-row="1" data-column="3"></div>
        <div class="row-column-box" data-row="1" data-column="4"></div>
        <div class="row-column-box" data-row="1" data-column="5"></div>
        <div class="row-column-box" data-row="2" data-column="1"></div>
        <div class="row-column-box" data-row="2" data-column="2"></div>
        <div class="row-column-box" data-row="2" data-column="3"></div>
        <div class="row-column-box" data-row="2" data-column="4"></div>
        <div class="row-column-box" data-row="2" data-column="5"></div>
        <div class="row-column-box" data-row="3" data-column="1"></div>
        <div class="row-column-box" data-row="3" data-column="2"></div>
        <div class="row-column-box" data-row="3" data-column="3"></div>
        <div class="row-column-box" data-row="3" data-column="4"></div>
        <div class="row-column-box" data-row="3" data-column="5"></div>
        <div class="row-column-box" data-row="4" data-column="1"></div>
        <div class="row-column-box" data-row="4" data-column="2"></div>
        <div class="row-column-box" data-row="4" data-column="3"></div>
        <div class="row-column-box" data-row="4" data-column="4"></div>
        <div class="row-column-box" data-row="4" data-column="5"></div>
        <div class="row-column-box" data-row="5" data-column="1"></div>
        <div class="row-column-box" data-row="5" data-column="2"></div>
        <div class="row-column-box" data-row="5" data-column="3"></div>
        <div class="row-column-box" data-row="5" data-column="4"></div>
        <div class="row-column-box" data-row="5" data-column="5"></div>
    </div>
</div>
<div id="thumbnail-list">
    <button id="reportButton" class="btn btn-primary">보고서 작성</button>
    <h2>썸네일</h2>
    <!-- Thumbnails will be added here dynamically -->
</div>
<div id="main-viewer">
    <!-- Main image viewer -->
<%--        <div id="overlay">--%>
<%--        <canvas id="overlayCanvas" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;"></canvas>--%>
<%--        </div>--%>
    <div id="viewportGrid">
    </div>
</div>
<input type="hidden" id="studyKey" value="${study.studykey}">


<!-- 리포트 모달 -->
<div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="reportModalLabel">리포트 작성</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="report-details" class="detail-section">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">예비판독의</h5>
                            <p class="card-text" id="preDoctor"></p>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">최종 판독의</h5>
                            <p class="card-text" id="finalDoctor"></p>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">환자코드</h5>
                            <p class="card-text" id="report-patientCode"></p>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">판독 상태</h5>
                            <p class="card-text" id="status"></p>
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">코멘트</h5>
                            <input type="text" class="form-control" id="comments">
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">검사소견</h5>
                            <input type="text" class="form-control" id="finding">
                        </div>
                    </div>
                    <div class="card mt-3">
                        <div class="card-body">
                            <h5 class="card-title">향후 치료 의견</h5>
                            <input type="text" class="form-control" id="futureComment">
                        </div>
                    </div>
                    <div class="button-container mt-3">
                        <button id="preliminary-button" class="btn btn-primary" style="display: none;"
                                onclick="savePreliminaryReport()">예비판독
                        </button>
                        <button id="final-button" class="btn btn-primary" style="display: none;"
                                onclick="saveFinalReport()">최종판독
                        </button>
                        <button id="note-button" class="btn btn-primary" style="display: none;" onclick="showNote()">소견서
                            작성
                        </button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/report.js"></script>
</body>
</html>