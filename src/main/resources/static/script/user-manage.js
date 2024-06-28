$(document).ready(function() {
    // 삭제 버튼 클릭 이벤트 핸들러
    $(".delete-button").click(function() {
        var doctorId = $(this).data("id");
        if (confirm("정말 삭제하시겠습니까?")) {
            $.ajax({
                url: "/user/delete",
                type: "DELETE",
                contentType: "application/json",
                data: JSON.stringify({ id: doctorId }),
                success: function(response) {
                    if (response) {
                        alert("삭제되었습니다.");
                        location.reload(); // 페이지를 새로고침하여 삭제된 내용을 반영
                    } else {
                        alert("삭제에 실패하였습니다.");
                    }
                },
                error: function() {
                    alert("에러가 발생하였습니다.");
                }
            });
        }
    });

    // 모달 관련 변수
    const editModal = $('#editModal');
    const closeEdit = $('#close-edit');

    const joinModal = $('#joinModal');
    const closeJoin = $('#close-join');

    // 수정 버튼 클릭 이벤트 핸들러
    $('.edit-button').on('click', function() {
        var doctorId = $(this).data('id');
        $.ajax({
            url: '/user/update/admin/' + doctorId,
            type: 'GET',
            success: function(response) {
                $('#edit-modal-body-content').html(response);
                editModal.show();

                // admin-edit.jsp의 스크립트를 실행
                initializeForm();
            },
            error: function() {
                alert('에러가 발생하였습니다.');
            }
        });
    });

    // 모달 닫기 버튼 클릭 이벤트 핸들러
    closeEdit.on('click', function() {
        editModal.hide();
        $('#edit-modal-body-content').html('');
    });

    // 모달 닫기 버튼 클릭 이벤트 핸들러
    closeJoin.on('click', function() {
        initializeValues();
        joinModal.hide();
    });

    $('.cancel-button').on('click',function (){
        initializeValues();
        joinModal.hide();
    })


    // 추가 버튼 클릭 이벤트 핸들러
    $('.add-button').on('click', function() {
        joinModal.show();
    });

    $('#joinForm').on('submit', function(event) {
        event.preventDefault();

        const name = $('#name').val().trim();
        const position = $('#position').val().trim();
        const department = $('#department').val().trim();
        const birth = $('#birth').val().trim();
        const phone = $('#phone').val().trim();
        const address = $('#address').val().trim();

        if (!name || !position || !department || !birth || !phone || !address) {
            alert('모든 필드를 입력해 주세요.');
        }else{
            const departmentCode = parseInt(department);
            const formData = {
                "name" : name,
                "position" : position,
                "departmentCode" : departmentCode,
                "birth" : birth,
                "phone" : phone,
                "address" : address
            }

            $.ajax({
                url: '/user/join',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                   if(response){
                       alert('등록 완료.')
                       location.reload(true);
                   }else{
                       alert('등록 실패')
                   }
                },
                error: function() {
                    alert('에러가 발생하였습니다.');
                }
            });
        }

    })

    // 모달 외부 클릭 시 모달 닫기 이벤트 핸들러
    $(window).on('click', function(event) {
        if ($(event.target).is(joinModal)) {
            initializeValues();
            joinModal.hide();
        }else if ($(event.target).is(editModal)) {
            editModal.hide();
            $('#edit-modal-body-content').html('');
        }
    });
});

function initializeValues(){
    $('#name').val("");
    $('#position').val("");
    $('#department').val("");
    $('#birth').val("");
    $('#phone').val("");
    $('#address').val("");
}

function initializeForm() {
    const container = $('.container');
    const userInfo = {
        id: container.data('user-id'),
        position: container.data('user-position'),
        departmentCode: container.data('user-department-code'),
        phone: container.data('user-phone')
    };

    $('#position').val(userInfo.position);
    $('#department').val(userInfo.departmentCode);
    $('#phone').val(userInfo.phone);

    $('#registerForm').on('submit', function(event) {
        event.preventDefault();

        const formData = {
            id: userInfo.id,
            position: $('#position').val(),
            departmentCode: $('#department').val(),
            phone: $('#phone').val()
        };

        $.ajax({
            url: '/user/update/admin',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                if (response) {
                    alert('수정되었습니다.');
                    window.location.href = '/admin/manage';
                } else {
                    alert('수정에 실패하였습니다.');
                }
            },
            error: function() {
                alert('에러가 발생하였습니다.');
            }
        });
    });
}
