document.addEventListener("DOMContentLoaded", function() {
    setupButtons();
});

function setupButtons() {
    const noteStatus = document.getElementById('noteStatus').value;
    const saveButton = document.getElementById('saveButton');
    const saveTempButton = document.getElementById('saveTempButton');
    const printButton = document.getElementById('printButton');

    if (noteStatus === '1') {
        saveButton.style.display = 'none';
        saveTempButton.style.display = 'none';
        printButton.style.display = 'block';
    } else {
        printButton.style.display = 'none';
    }
}

function confirmSave() {
    if (confirm("저장하시겠습니까?")) {
        saveNote();
    }
}

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
        finalDoctor: document.getElementById('finalDoctor').value,
        patientCode: document.getElementById('patientCode').value,
        disease: document.getElementById('disease').value,
        treatmentPeriod: document.getElementById('treatmentPeriod').value,
        finding: document.getElementById('finding').value,
        comments: document.getElementById('doctorComment').value,
        futureComment: document.getElementById('futureComment').value,
        remark: document.getElementById('remark').value
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

function cancel() {
    window.location.href = '/main'; // 메인 페이지로 리다이렉션
}

function MovePrintNote() {
    const studykey = document.getElementById('studykey').value;
    const url = `http://localhost:8080/note/printNote/${studykey}`;
    window.open(url, '_blank');
}

