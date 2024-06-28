document.addEventListener('DOMContentLoaded', function() {

});

function getStatusText(status) {
    switch(status) {
        case 3:
            return '판독안함';
        case 5:
            return '예비판독';
        case 6:
            return '판독완료';
        default:
            return '';
    }
}

function getPositionText(position) {
    switch(position) {
        case 'professor':
            return '교수';
        case 'intern':
            return '인턴';
        case 'fellow':
            return '펠로우';
        case 'resident':
            return '레지던트';
        default:
            return position;
    }
}

function showReportDetails(studykey) {
    fetch(`/report?studykey=${studykey}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const report = data.report;
            const preDoctorPositionText = getPositionText(data.preDoctorPosition);
            const finalDoctorPositionText = getPositionText(data.finalDoctorPosition);

            document.getElementById('preDoctor').textContent = `${data.preDoctorName || ''} ${preDoctorPositionText || ''}`;
            document.getElementById('doctor').textContent = report.doctor || '';
            document.getElementById('finalDoctor').textContent = `${data.finalDoctorName || ''} ${finalDoctorPositionText || ''}`;
            document.getElementById('report-patientCode').textContent = report.patientCode || '';
            document.getElementById('status').textContent = getStatusText(report.status);
            document.getElementById('comments').value = report.comments || '';
            document.getElementById('futureComment').value = report.futureComment || '';

            // studykey 저장
            document.getElementById('report-details').dataset.studykey = report.studykey;

            const preliminaryButton = document.getElementById('preliminary-button');
            if (report.status === 3) {
                preliminaryButton.style.display = 'block';
            } else {
                preliminaryButton.style.display = 'none';
            }

            const finalButton = document.getElementById('final-button');
            if (report.status === 6) {
                finalButton.style.display = 'none';
            } else {
                finalButton.style.display = 'block';
            }
        })
        .catch(error => {
            console.error('리포트 불러오기 중 오류가 발생했습니다:', error);
        });
}

function savePreliminaryReport() {
    if (userPosition === 'intern') {
        alert("인턴은 예비 판독을 할 수 없습니다.");
        return;
    }

    if (!confirm("예비 판독을 저장하시겠습니까?")) {
        return;
    }

    const studykey = document.getElementById('report-details').dataset.studykey;
    const patientCode = document.getElementById('report-patientCode').textContent;
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    fetch(`/report?studykey=${studykey}`)
        .then(response => response.json())
        .then(data => {
            const existingReport = data.report;

            const updatedReport = {
                ...existingReport,
                status: 5,
                comments,
                finding,
                futureComment,
                preDoctor: userId,
                modDate: new Date().toISOString()
            };

            return fetch(`/report/preliminary`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedReport)
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(result => {
            alert(result);
            // Refresh the status to reflect changes
            showReportDetails(studykey);
        })
        .catch(error => {
            console.error('저장 중 오류가 발생했습니다:', error);
        });
}

function saveFinalReport() {
    if (userPosition !== 'fellow' && userPosition !== 'professor') {
        alert("펠로우와 교수만 최종 판독을 할 수 있습니다.");
        return;
    }

    if (!confirm("최종 판독을 저장하시겠습니까?")) {
        return;
    }

    const studykey = document.getElementById('report-details').dataset.studykey;
    const patientCode = document.getElementById('report-patientCode').textContent;
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    fetch(`/report?studykey=${studykey}`)
        .then(response => response.json())
        .then(data => {
            const existingReport = data.report;

            const updatedReport = {
                ...existingReport,
                status: 6,
                comments,
                finding,
                futureComment,
                preDoctor: existingReport.preDoctor, // 기존 예비 판독의 정보를 유지합니다.
                finalDoctor: userId,
                modDate: new Date().toISOString()
            };

            return fetch(`/report/final`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedReport)
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(result => {
            alert(result);
            // Refresh the status to reflect changes
            showReportDetails(studykey);
        })
        .catch(error => {
            console.error('저장 중 오류가 발생했습니다:', error);
        });
}
