function showPatientDetails(pid) {
    fetch(`/patient?pid=${pid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(patient => {
            document.getElementById('patient-code').textContent = patient.code || '';
            document.getElementById('patient-name').textContent = patient.name || '';
            document.getElementById('patient-sex').textContent = patient.sex || '';
            document.getElementById('patient-birth').textContent = patient.birth || '';
            document.getElementById('patient-smoking').value = patient.smoking ? 'true' : 'false';
            document.getElementById('patient-drinking').value = patient.drinking ? 'true' : 'false';
            document.getElementById('patient-history').value = patient.history || '';
            document.getElementById('patient-caution').value = patient.caution || '';

            const saveButton = document.getElementById('save-button');
            if (pid) {
                saveButton.style.display = 'block';
            } else {
                saveButton.style.display = 'none';
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function savePatientDetails() {
    const pid = document.getElementById('patient-code').textContent;
    const name = document.getElementById('patient-name').textContent;
    const sex = document.getElementById('patient-sex').textContent;
    const birth = document.getElementById('patient-birth').textContent;
    const smoking = document.getElementById('patient-smoking').value === 'true';
    const drinking = document.getElementById('patient-drinking').value === 'true';
    const history = document.getElementById('patient-history').value;
    const caution = document.getElementById('patient-caution').value;

    const data = {
        code: pid,
        name,
        sex,
        birth,
        smoking,
        drinking,
        history,
        caution
    };

    fetch(`/patient/edit`, {
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
        })
        .catch(error => {
            console.error('저장 중 오류가 발생했습니다:', error);
        });
}
