// note.js
document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 후 초기 데이터 로드

    fetchNoteDetails(${note.studykey});

    // 저장 버튼 클릭 시
    document.getElementById("saveBtn").addEventListener("click", function() {
        saveNote();
    });

    // PDF 다운로드 버튼 클릭 시
    document.getElementById("downloadBtn").addEventListener("click", function() {
        downloadPDF(${note.studykey});
    });
});

function fetchNoteDetails(studykey) {
    fetch(`/note/${studykey}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("disease").value = data.disease;
            document.getElementById("treatmentPeriod").value = data.treatmentPeriod;
            document.getElementById("usage").value = data.usage;
        })
        .catch(error => console.error('Error fetching note details:', error));
}

function saveNote() {
    const formData = new FormData(document.getElementById("noteForm"));
    fetch(`/note/${note.studykey}`, {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to save note');
            }
            alert('저장되었습니다.');
        })
        .catch(error => console.error('Error saving note:', error));
}

function downloadPDF(studykey) {
    window.location.href = `/note/${studykey}/download/pdf`;
}
