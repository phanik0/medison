document.addEventListener("DOMContentLoaded", function() {
    setupButtons();
    setupSaveButton();
    setupSaveTempButton();
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

function setupSaveButton() {
    document.getElementById('saveButton').addEventListener('click', () => handleSave(1));
}

function setupSaveTempButton() {
    document.getElementById('saveTempButton').addEventListener('click', () => handleSave(0));
}

function handleSave(status) {
    const finalDoctor = document.getElementById('finalDoctor').value;
    const currentUserIdElement = document.getElementById('currentUserId');

    if (!currentUserIdElement) {
        alert('현재 사용자 ID를 찾을 수 없습니다.');
        return;
    }

    const currentUserId = currentUserIdElement.value;

    if (finalDoctor !== currentUserId) {
        alert('최종판독의만 작성이 가능합니다');
        return;
    }

    if (status === 1 && !validateNoteFields()) {
        alert('병명, 검사 소견, 진료의사 의견 및 향후 치료 의견을 입력해 주세요.');
        return;
    }

    saveNoteWithStatus(status);
}

function validateNoteFields() {
    const disease = document.getElementById('disease').value;
    const finding = document.getElementById('finding').value;
    const comments = document.getElementById('doctorComment').value;
    const futureComment = document.getElementById('futureComment').value;

    return disease && finding && comments && futureComment;
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
    window.location.href = `/note/printNote/${studykey}`;
}
