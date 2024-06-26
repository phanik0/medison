function showPatientDetails(pid) {
    fetch(`/patient?pid=${pid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(patient => {
            const patientDetails = document.getElementById('patient-details');
            patientDetails.innerHTML = `
                <h2>환자 정보</h2>
                <table>
                    <tr><th>코드</th><td>${patient.code}</td></tr>
                    <tr><th>이름</th><td>${patient.name}</td></tr>
                    <tr><th>성별</th><td>${patient.sex}</td></tr>
                    <tr><th>생년월일</th><td>${patient.birth}</td></tr>
                    <tr><th>흡연</th><td>${patient.smoking ? 'Yes' : 'No'}</td></tr>
                    <tr><th>음주</th><td>${patient.drinking ? 'Yes' : 'No'}</td></tr>
                    <tr><th>의료 이력</th><td>${patient.history}</td></tr>
                    <tr><th>주의 사항</th><td>${patient.caution}</td></tr>
                </table>`;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
