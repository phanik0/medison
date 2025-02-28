document.addEventListener('DOMContentLoaded', async function () {
    // 서버로부터 데이터를 가져옵니다
    const response = await fetch('/log/list', {
        method: 'GET',
    });

    // JSON 데이터를 파싱합니다
    const readLog = await response.json();

    const recordsPerPage = 10;
    let currentPage = 1;

    function displayLogs() {
        const startIndex = (currentPage - 1) * recordsPerPage;
        const endIndex = Math.min(startIndex + recordsPerPage, readLog.length);
        const logsToDisplay = readLog.slice(startIndex, endIndex);

        let tableBody = "";
        logsToDisplay.forEach((log, index) => {
            tableBody += `
                <tr>
                    <td>${startIndex + index + 1}</td>
                    <td>${log.studykey}</td>
                    <td>${log.regDate}</td>
                    <td>${log.userName}</td>
                    <td>${log.userPhone}</td>
                    <td>${log.userBirth}</td>
                    <td>${log.userAddress}</td>
                    <td>${log.department}</td>
                    <td>${log.position}</td>
                </tr>
            `;
        });

        document.getElementById("logTableBody").innerHTML = tableBody;
    }

    function setupPagination() {
        const totalPages = Math.ceil(readLog.length / recordsPerPage);
        let paginationHTML = `
            <button id="prevPage" ${currentPage === 1 ? 'disabled' : ''}><</button>
            <span>${currentPage}/${totalPages}</span>
            <button id="nextPage" ${currentPage === totalPages ? 'disabled' : ''}>></button>
        `;

        document.getElementById("pagination").innerHTML = paginationHTML;

        document.getElementById("prevPage").addEventListener("click", function() {
            if (currentPage > 1) {
                changePage(currentPage - 1);
            }
        });

        document.getElementById("nextPage").addEventListener("click", function() {
            const totalPages = Math.ceil(readLog.length / recordsPerPage);
            if (currentPage < totalPages) {
                changePage(currentPage + 1);
            }
        });
    }

    function changePage(page) {
        currentPage = page;
        displayLogs();
        setupPagination();
    }

    // 초기화
    displayLogs();
    setupPagination();
});
