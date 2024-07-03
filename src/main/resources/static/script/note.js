document.addEventListener("DOMContentLoaded", function () {
    // note-button에 클릭 이벤트 추가
    document.getElementById("note-button").addEventListener("click", function() {
        // studyKey를 포함하여 showNote 호출
        const studyKey = document.getElementById("studyKey").value;
        showNote(studyKey);
    });
});

function showNote(studyKey) {
    // 요소가 존재하는지 확인하고 값 추출
    const getValueById = (id) => {
        const element = document.getElementById(id);
        return element ? element.value : '';
    };

    const demoNote = {
        pName: getValueById("patient-name"),
        pBirth: getValueById("patient-birth"),
        disease: getValueById("disease"),
        firstDate: getValueById("firstDate"),
        lastDate: getValueById("lastDate"),
        treatmentPeriod: getValueById("treatmentPeriod"),
        diseaseHistory: getValueById("diseaseHistory"),
        finding: getValueById("finding"),
        doctorComment: getValueById("comments"),
        futureComment: getValueById("futureComment"),
        doctorLicense: getValueById("doctorLicense"),
        doctorName: getValueById("doctorName"),
        hospitalAddress: getValueById("hospitalAddress")
    };

    // studyKey를 가져와 URL로 이동
    const url = `/note/${studyKey}`;
    window.location.href = url;
}
