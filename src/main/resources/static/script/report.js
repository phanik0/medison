document.addEventListener('DOMContentLoaded', function() {
    // 초기화 작업은 필요 시 여기에 작성
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
    const studykey = document.getElementById('report-details').dataset.studykey;
    const patientCode = document.getElementById('report-patientCode').textContent;
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    const data = {
        studykey: parseInt(studykey),
        patientCode,
        status: 5,
        comments,
        finding,
        futureComment,
        preDoctor: userId, // 사용자 ID로 설정
        regDate: new Date().toISOString(),
        modDate: new Date().toISOString()
    };

    fetch(`/report/preliminary`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
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
    const studykey = document.getElementById('report-details').dataset.studykey;
    const patientCode = document.getElementById('report-patientCode').textContent;
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    const data = {
        studykey: parseInt(studykey),
        patientCode,
        status: 6,
        comments,
        finding,
        futureComment,
        finalDoctor: userId, // 사용자 ID로 설정
        regDate: new Date().toISOString(),
        modDate: new Date().toISOString()
    };

    fetch(`/report/final`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
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
