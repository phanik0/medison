<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="admin-update-container" class="container"
     data-user-id="${userInfo.id}"
     data-user-position="${userInfo.position}"
     data-user-department-code="${userInfo.departmentCode}"
     data-user-phone="${userInfo.phone}">
    <h1 class="title">${userInfo.id} 수정</h1>
    <form id="registerForm">
        <div class="form-group">
            <label for="position">직급</label>
            <select id="position" name="position" >
                <option value="professor">교수</option>
                <option value="intern">인턴</option>
                <option value="fellow">펠로우</option>
                <option value="resident">레지던트</option>
            </select>
        </div>
        <div class="form-group">
            <label for="department">근무과</label>
            <select id="department" name="department">
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
        <div class="form-group">
            <label for="phone">연락처</label>
            <input type="text" id="phone" name="phone" required>
        </div>
        <div class="form-actions">
            <button type="submit" class="confirm-button">수정하기</button>
        </div>
    </form>
</div>
