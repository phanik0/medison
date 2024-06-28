// bookmark.js

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
            userId: userId
        }),
        success: function(response) {
            alert('북마크가 저장되었습니다.');
            $('#bookmarkModal').dialog('close');
        },
        error: function(error) {
            alert('북마크 저장 중 오류가 발생했습니다.');
        }
    });
}

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
});
