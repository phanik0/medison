
function openBookmarkModal(studyKey) {
    $('#bookmarkStudyKey').val(studyKey);
    $('#bookmarkModal').dialog('open');
}

function saveBookmark() {
    var studyKey = $('#bookmarkStudyKey').val();
    var comments = $('#bookmarkComments').val();
    var code = $('#bookmarkButton' + studyKey).data('code');

    // undefined 체크 및 0으로 변환
    code = code !== undefined && code !== 'undefined' && code !== '' ? code : 0;

    $.ajax({
        url: '/bookmark/save',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            studykey: studyKey,
            comments: comments,
            userId: userId,
            regDate: new Date().toISOString()
        }),
        success: function(response) {
            alert('북마크가 저장되었습니다.');
            $('#bookmarkModal').dialog('close');
            var button = $('#bookmarkButton' + studyKey);
            button.data('bookmarked', true);
            button.data('code', response.code); // 서버에서 새로 생성된 코드를 반환한다고 가정
            button.addClass('bookmarked');
            button.text('⭐');
        },
        error: function(error) {
            alert('북마크 저장 중 오류가 발생했습니다.');
        }
    });
}

function deleteBookmark(code) {
    if (code === 0) {
        alert('유효한 북마크 코드가 없습니다.');
        return;
    }

    $.ajax({
        type: 'DELETE',
        url: '/bookmark/delete/' + code,
        success: function(response) {
            alert('북마크가 삭제되었습니다.');
            var button = $('.bookmark-btn[data-code="' + code + '"]');
            button.data('bookmarked', false).text('☆').removeClass('bookmarked').addClass('not-bookmarked');
            button.data('code', 0); // 북마크 코드 초기화
        },
        error: function(error) {
            console.log('Error:', error);
        }
    });
}

function loadUserBookmarks() {
    // 사용자 북마크 정보를 서버에서 불러오는 로직을 구현합니다.
    $.ajax({
        url: '/bookmark/list',
        type: 'GET',
        data: { userId: userId },
        contentType: 'application/json',
        success: function(response) {
            // 응답 데이터를 바탕으로 북마크 버튼 상태를 업데이트합니다.
            response.forEach(function(bookmark) {
                var button = $('#bookmarkButton' + bookmark.studykey);
                button.data('bookmarked', true);
                button.data('code', bookmark.code);
                button.addClass('bookmarked');
                button.text('⭐');
            });
        },
        error: function(error) {
            console.error('사용자 북마크 정보를 불러오는 중 오류가 발생했습니다.', error);
        }
    });
}

$(document).on('click', '.delete-bookmark-btn', function() {
    var code = $(this).data('code');
    deleteBookmark(code);
});

$(function() {
    // 북마크 모달 창 크기 설정
    $("#bookmarkModal").dialog({
        autoOpen: false,
        width: 550, // 너비 설정
        height: 250, // 높이 설정
        modal: true,
        buttons: {
            "추가": function() {
                saveBookmark(); // 북마크 추가 로직
            },
            "취소": function() {
                $(this).dialog("close");
            }
        }
    });
});
