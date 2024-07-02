$(function() {
    $("#bookmarkModal").dialog({
        autoOpen: false,
        modal: true,
        buttons: {
            "저장": saveBookmark,
            "취소": function() {
                $(this).dialog('close');
            }
        }
    });

    // 페이지 로드 시 사용자 북마크 정보 불러오기
    loadUserBookmarks();

    // 북마크 버튼 클릭 이벤트
    $('.bookmark-btn').click(function() {
        var studyKey = $(this).data('study-key');
        var code = $(this).data('code');

        if ($(this).data('bookmarked')) {
            deleteBookmark(code, studyKey);
        } else {
            openBookmarkModal(studyKey);
        }
    });
});

function openBookmarkModal(studyKey) {
    $('#bookmarkStudyKey').val(studyKey);
    $('#bookmarkModal').dialog('open');
}

function saveBookmark() {
    var studyKey = $('#bookmarkStudyKey').val();
    var comments = $('#bookmarkComments').val();

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
            button.addClass('bookmarked');
            button.text('⭐');
        },
        error: function(error) {
            alert('북마크 저장 중 오류가 발생했습니다.');
        }
    });
}

function deleteBookmark(code, studyKey) {
    console.log("Deleting bookmark with code:", code);
    $.ajax({
        url: '/bookmark/delete',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            code: code,
            studykey: studyKey,
            userId: userId
        }),
        success: function(response) {
            alert('북마크가 삭제되었습니다.');
            var button = $('#bookmarkButton' + studyKey);
            button.data('bookmarked', false);
            button.removeClass('bookmarked');
            button.text('☆');
        },
        error: function(error) {
            alert('북마크 삭제 중 오류가 발생했습니다.');
            console.error('Error deleting bookmark:', error);
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
                button.addClass('bookmarked');
                button.text('⭐');
            });
        },
        error: function(error) {
            console.error('사용자 북마크 정보를 불러오는 중 오류가 발생했습니다.', error);
        }
    });
}
