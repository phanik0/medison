<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<jsp:include page="../module/header.jsp"/>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/manage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/script/user-manage.js"></script>
</head>
<body>

<c:if test="${not user.admin}">
    <c:redirect url="/main"></c:redirect>
</c:if>

<button class="add-button">추가</button>
<div class="profile-container">
    <c:forEach var="doctor" items="${infos}">
        <c:if test="${not doctor.admin}">
            <div class="profile-card">
                <div class="profile-image"><img src="${pageContext.request.contextPath}/image/profile.png"></div>
                <div class="profile-details">
                    <p>이름 : ${doctor.name}</p>
                    <p>직급
                        : ${doctor.position.equals("professor")?"교수":doctor.position.equals("intern")?"인턴":doctor.position.equals("fellow")?"펠로우":"레지던트"}</p>
                    <p>아이디 : ${doctor.id}</p>
                    <p>연락처 : ${doctor.phone}</p>
                    <div class="profile-actions">
                        <button class="edit-button" data-id="${doctor.id}">수정</button>
                        <button class="delete-button" data-id="${doctor.id}">삭제</button>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>

<!-- 모달 창 -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <span id="close-edit" class="close">&times;</span>
        <div id="edit-modal-body-content"></div>
    </div>
</div>


<!-- 모달 창 -->
<div id="joinModal" class="modal">
    <div class="modal-content">
        <span id="close-join" class="close">&times;</span>
        <div id="join-modal-body-content">
            <h1 class="title">의사 등록</h1>
            <form id="joinForm">
                <div class="form-group">
                    <input type="text" id="name" name="name" placeholder="이름">
                </div>
                <div class="form-group">
                    <label for="position">직급</label>
                    <select id="position" name="position">
                        <option value="">선택</option>
                        <option value="professor">교수</option>
                        <option value="intern">인턴</option>
                        <option value="fellow">펠로우</option>
                        <option value="resident">레지던트</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="department">근무과</label>
                    <select id="department" name="department">
                        <option value="">선택</option>
                        <option value="2">신경과</option>
                        <option value="3">심장내과</option>
                        <option value="4">종양내과</option>
                        <option value="5">소아과</option>
                        <option value="6">정형외과</option>
                        <option value="7">영상의학과</option>
                        <option value="8">피부과</option>
                        <option value="9">안과</option>
                        <option value="10">소화기내과</option>
                        <option value="11">내분비내과</option>
                        <option value="12">혈액내과</option>
                        <option value="13">신장내과</option>
                        <option value="14">비뇨기과</option>
                        <option value="15">호흡기내과</option>
                        <option value="16">류마티스내과</option>
                        <option value="17">알레르기내과</option>
                        <option value="18">마취과</option>
                        <option value="19">응급의학과</option>
                        <option value="20">가정의학과</option>
                        <option value="21">일반외과</option>
                        <option value="22">내과</option>
                        <option value="23">산부인과</option>
                        <option value="24">이비인후과</option>
                        <option value="25">병리과</option>
                        <option value="26">재활의학과</option>
                        <option value="27">성형외과</option>
                        <option value="28">정신건강의학과</option>
                    </select>
                </div>
                <div id="join-form-group" class="form-group">
                    <input type="text" id="birth" name="birth" placeholder="생년월일">
                    <input type="text" id="phone" name="phone" placeholder="연락처">
                    <input type="text" id="address" name="address" placeholder="주소">
                </div>
                <div class="form-actions">
                    <button type="submit" class="confirm-button">등록</button>
                    <button type="button" class="cancel-button">취소</button>
                </div>
            </form>

        </div>
    </div>
</div>

</body>
</html>
