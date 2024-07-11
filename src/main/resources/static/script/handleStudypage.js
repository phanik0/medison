function handleDblClick(studykey, userId) {
    $.ajax({
        url: '/log/' + studykey + '/' + userId,
        type: 'POST',
        success: function (response) {
            if (response) {
                redirectToStudy(studykey);
            } else {
                if (confirm('의료법 시행규칙 제39조의15항에 의거, 해당 검사에 대해 영상정보 열람기록을 남기는 것에 대해 동의 하십니까?')) {
                    redirectToStudy(studykey);
                } else {
                    alert('열람 기록을 남기는 것에 대해 동의하셔야 합니다.');
                }
            }
        },
        error: function () {
            alert('에러가 발생하였습니다.')
        }
    })
}

function redirectToStudy(studykey) {
    window.location.href = '/study/' + studykey;
}

function handleClick(studykey, pid) {
    showPatientDetails(pid);
    showReportDetails(studykey);
}
