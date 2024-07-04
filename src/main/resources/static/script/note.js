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

function cancel() {
    // 취소 버튼을 클릭했을 때 수행할 작업
    window.location.href = '/main'; // 예: 메인 페이지로 리다이렉션
}

function printNote() {
    const noteContent = document.getElementById('noteContent');

    html2canvas(noteContent).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgWidth = pdf.internal.pageSize.width;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        pdf.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
        pdf.save('note.pdf');

        // PDF 미리보기
        const pdfBlob = pdf.output('blob');
        const url = URL.createObjectURL(pdfBlob);
        window.open(url);
    });
}
