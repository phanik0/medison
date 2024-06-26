document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById("reportModal");
    const span = document.getElementsByClassName("close")[0];

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
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

function showReportDetails(studykey) {
    fetch(`/report?studykey=${studykey}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(report => {
            const reportDetails = document.getElementById('report-details');
            reportDetails.innerHTML = `
                <h2>검사 리포트</h2>
                <div class="report-container">
                    <div class="report-section">
                        <table>
                            <tr><th>코멘트</th><td><input type="text" id="comments" value="${report.comments || ''}"></td></tr>
                            <tr><th>검사소견</th><td><input type="text" id="finding" value="${report.finding || ''}"></td></tr>
                            <tr><th>향후 치료 의견</th><td><input type="text" id="futureComment" value="${report.futureComment || ''}"></td></tr>
                        </table>
                    </div>
                    <div class="report-section">
                        <table>
                            <tr><th>예비판독의</th><td>${report.preDoctor || ''}</td></tr>
                            <tr><th>판독의</th><td>${report.doctor || ''}</td></tr>
                            <tr><th>최종 판독의</th><td>${report.finalDoctor || ''}</td></tr>
                            <tr><th>환자코드</th><td>${report.patientCode || ''}</td></tr>
                            <tr><th>판독 상태</th><td>${getStatusText(report.status)}</td></tr>
                        </table>
                    </div>
                </div>
                <button onclick="savePreliminaryReport(${report.studykey})">예비판독</button>
                <button onclick="saveFinalReport(${report.studykey})">최종판독</button>
            `;
        })
        .catch(error => {
            console.error('리포트 불러오기 중 오류가 발생했습니다:', error);
        });
}

function savePreliminaryReport(studykey) {
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    const data = {
        studykey,
        comments,
        finding,
        futureComment,
        status: 5 // 예비판독 상태
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
            return response.json();
        })
        .then(result => {
            alert('예비판독이 저장되었습니다.');
            document.getElementById("reportModal").style.display = "none";
        })
        .catch(error => {
            console.error('저장 중 오류가 발생했습니다:', error);
        });
}

function saveFinalReport(studykey) {
    const comments = document.getElementById('comments').value;
    const finding = document.getElementById('finding').value;
    const futureComment = document.getElementById('futureComment').value;

    const data = {
        studykey,
        comments,
        finding,
        futureComment,
        status: 6 // 최종판독 상태
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
            return response.json();
        })
        .then(result => {
            alert('최종판독이 저장되었습니다.');
            document.getElementById("reportModal").style.display = "none";
        })
        .catch(error => {
            console.error('저장 중 오류가 발생했습니다:', error);
        });
}