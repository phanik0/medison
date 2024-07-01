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
        if ($(this).data('bookmarked')) {
            deleteBookmark(studyKey);
        } else {
            openBookmarkModal(studyKey);
        }
    });
});

// function loadUserBookmarks() {
//     $.ajax({
//         url: '/bookmark/get',
//         type: 'GET',
//         data: { userId: userId },
//         success: function(response) {
//             console.log("북마크 데이터:", response); // 응답 데이터를 로그로 출력
//             var bookmarks = response;
//             for (var studyKey in bookmarks) {
//                 var button = $('#bookmarkButton' + studyKey);
//                 console.log("버튼:", button); // 버튼 요소를 로그로 출력
//                 if (bookmarks[studyKey]) {
//                     button.data('bookmarked', true).addClass('bookmarked');
//                 } else {
//                     button.data('bookmarked', false).removeClass('bookmarked');
//                 }
//                 button.text('⭐');
//             }
//         },
//         error: function(error) {
//             console.log("오류 응답:", error); // 오류 응답을 로그로 출력
//             alert('북마크 정보를 불러오는 중 오류가 발생했습니다.');
//         }
//     });
// }


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

function deleteBookmark(studyKey) {
    $.ajax({
        url: '/bookmark/delete',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            studykey: studyKey,
            userId: userId
        }),
        success: function(response) {
            alert('북마크가 삭제되었습니다.');
            var button = $('#bookmarkButton' + studyKey);
            button.data('bookmarked', false);
            button.removeClass('bookmarked');
            button.text('⭐');
        },
        error: function(error) {
            alert('북마크 삭제 중 오류가 발생했습니다.');
        }
    });
}