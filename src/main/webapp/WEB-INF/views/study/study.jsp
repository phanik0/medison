<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Study DICOM Viewer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/study.css">
    <script src="${pageContext.request.contextPath}/dist/bundle.js"></script>
    <script src="${pageContext.request.contextPath}/script/report.js"></script>

</head>
<body>
<h1>Study DICOM Viewer</h1>
<div id="thumbnail-list">
    <h2>PastStudyList</h2>
    <div>
        <select id="studySelector"></select>
    </div>
    <h2>썸네일</h2>
    <!-- Thumbnails will be added here dynamically -->
</div>
<div id="main-viewer">
    <!-- Main image viewer -->
    <div id="viewportGrid">

    </div>
</div>
<input type="hidden" id="studyKey" value="${study.studykey}">
<button id="reportButton" class="btn btn-primary">보고서 작성</button>

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
                        <button id="preliminary-button" class="btn btn-primary" style="display: none;" onclick="savePreliminaryReport()">예비판독</button>
                        <button id="final-button" class="btn btn-primary" style="display: none;" onclick="saveFinalReport()">최종판독</button>
                        <button id="note-button" class="btn btn-primary" style="display: none;" onclick="showNote()">소견서 작성</button>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>


<c:out value="${study}"></c:out>

<c:out value="${study.studykey}"></c:out>
</body>
</html>
