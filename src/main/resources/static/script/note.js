// function showReportDetails(studykey) {
//     fetch(`/note/show`)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             }
//             return response.json();
//         })
// }

function fetchNoteDetails(studykey) {

    var urlParams = new URLSearchParams(window.location.search);
    var studykey = urlParams.get('studykey');

    if (!studykey) {
        console.error('studykey 파라미터를 찾을 수 없습니다.');
        return;
    }

    fetch(`/note/${studykey}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displayNoteDetails(data);
        })
        .catch(error => {
            console.error('소견서 불러오기 중 오류가 발생했습니다:', error);
        });
}

function displayNoteDetails(note) {
    // DOM 사용하여 노트 정보를 각 필드에 동적으로 표시
    document.getElementById("code").innerText = note.code;
    document.getElementById("studykey").innerText = note.studykey;
    document.getElementById("finalDoctor").innerText = note.finalDoctor;
    document.getElementById("patientCode").innerText = note.patientCode;
    document.getElementById("disease").innerText = note.disease;
    document.getElementById("treatmentPeriod").innerText = note.treatmentPeriod;
    document.getElementById("finding").innerText = note.finding;
    document.getElementById("comments").innerText = note.comments;
    document.getElementById("futureComment").innerText = note.futureComment;
    document.getElementById("usage").innerText = note.usage;
    document.getElementById("regDate").innerText = note.regDate;
    document.getElementById("modDate").innerText = note.modDate;
}

function downloadPDF(studykey) {
    window.location.href = `/note/download/${studykey}`;
}