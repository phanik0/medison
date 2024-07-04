function saveNote() {
    const status = 1;
    saveNoteWithStatus(status);
}

function saveTemporaryNote() {
    const status = 0;
    saveNoteWithStatus(status);
}

function saveNoteWithStatus(status) {
    const noteData = {
        studykey: document.getElementById('studykey').value,
        status: status,
        finalDoctor: document.getElementById('finalDoctor').value, // value로 변경
        patientCode: document.getElementById('patientCode').value,
        disease: document.getElementById('disease').value,
        treatmentPeriod: document.getElementById('treatmentPeriod').value, // value로 변경
        finding: document.getElementById('finding').value,
        comments: document.getElementById('doctorComment').value,
        futureComment: document.getElementById('futureComment').value,
        purpose: document.getElementById('purpose').value
    };

    fetch('/note/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(noteData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(result => {
            alert(result);
            // 필요에 따라 페이지를 리로드하거나 다른 작업 수행
        })
        .catch(error => {
            console.error('노트 저장 중 오류가 발생했습니다:', error);
        });
}

function confirmSave() {
    if (confirm('저장하시겠습니까?')) {
        saveNote();
    }
}

function printNote() {
    // 출력 기능 구현
    alert('출력 기능은 아직 구현되지 않았습니다.');
}

function cancel() {
    // 취소 버튼을 클릭했을 때 수행할 작업
    window.location.href = '/main'; // 예: 메인 페이지로 리다이렉션
}

function setupButtons() {
    var noteStatus = parseInt(document.getElementById('noteStatus').value);
    var saveButton = document.getElementById('saveButton');
    var saveTempButton = document.getElementById('saveTempButton');
    var printButton = document.getElementById('printButton');

    if (noteStatus === 1 ) {
        saveButton.style.display = 'none';
        saveTempButton.style.display = 'none';
        printButton.style.display = 'inline-block';
    } else {
        printButton.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    setupButtons();
});
