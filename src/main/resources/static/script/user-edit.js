$(document).ready(function () {
    $('#registerForm').on('submit', function (e) {
        e.preventDefault();

        const curPassword = $('#cur-password').val().trim();
        const newPassword = $('#new-password').val().trim();
        const userId = $('.container').data("user-id");

        if (!userId || !curPassword || !newPassword) {
            alert('모든 필드를 입력해 주세요.')
        } else {
            const formData = {
                "id": userId,
                "password": newPassword,
                "currentPassword": curPassword
            }

            $.ajax({
                url: '/user/update/me',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function (response) {
                    if (response) {
                        alert('수정완료. 다시 로그인 해 주세요.');
                        location.href = '/user/login';
                    } else {
                        alert('수정실패. 비밀번호를 다시 확인해 주세요.')
                    }
                },
                error: function () {
                    alert('에러가 발생하였습니다.')
                }
            })
        }
    })
})