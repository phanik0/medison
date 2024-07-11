let userId;
let userName;
let userPosition;

document.addEventListener('DOMContentLoaded', function () {
    const userValueElement = document.getElementById("user-value");

    userId = userValueElement.dataset.userId;
    userName = userValueElement.dataset.userName;
    userPosition = userValueElement.dataset.userPosition;

    $(function () {
        $("#calendar").datepicker({
            numberOfMonths: 1,
            onSelect: function (dateText, inst) {
                if (!this.startDate) {
                    this.startDate = dateText;
                    $("#startDate").val(dateText);
                } else if (!this.endDate) {
                    this.endDate = dateText;
                    $("#endDate").val(dateText);
                    this.startDate = null;
                    this.endDate = null;
                }
            }
        });

        $("#startDate, #endDate").datepicker({
            dateFormat: "yy-mm-dd"
        });

        $("#searchForm").submit(function (event) {
            const startDate = $("#startDate").datepicker("getDate");
            const endDate = $("#endDate").datepicker("getDate");

            if (startDate && endDate) {
                const formattedStartDate = $.datepicker.formatDate("yy-mm-dd", startDate);
                const formattedEndDate = $.datepicker.formatDate("yy-mm-dd", endDate);

                $("#startDate").val(formattedStartDate);
                $("#endDate").val(formattedEndDate);
            }
        });

        document.getElementById('userName').textContent = userName;
        document.getElementById('userPosition').textContent = translatePosition(userPosition);

        loadUserBookmarks();
    });
});

function translatePosition(position) {
    switch (position) {
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



let clickTimer = null;

function handleClick(studykey, pid) {
    if (clickTimer === null) {
        clickTimer = setTimeout(function () {
            showPatientDetails(pid);
            showReportDetails(studykey);
            clickTimer = null;
        }, 300); // 300ms 지연 시간 설정
    }
}
