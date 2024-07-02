<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="module/header.jsp" %>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bookmark.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
</head>
<body>
<main>
    <div class="main-content">
        <h2>북마크 목록</h2>
        <table>
            <thead>
                <tr>
                    <th>코드</th>
                    <th>Study Key</th>
                    <th>사용자 ID</th>
                    <th>코멘트</th>
                    <th>등록 날짜</th>
                    <th>액션</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="bookmark" items="${bookmarks}">
                    <tr>
                        <td>${bookmark.code}</td>
                        <td>${bookmark.studykey}</td>
                        <td>${bookmark.userId}</td>
                        <td>${bookmark.comments}</td>
                        <td>${bookmark.regDate}</td>
                        <td>
                            <button class="delete-bookmark-btn" data-code="${bookmark.code}">삭제</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button onclick="location.href='/main'">메인으로 돌아가기</button>
    </div>
</main>
<script>
    $(document).on('click', '.delete-bookmark-btn', function() {
        var code = $(this).data('code');
        $.ajax({
            url: '/bookmark/delete/' + code,
            type: 'DELETE',
            success: function(response) {
                alert('북마크가 삭제되었습니다.');
                location.reload();
            },
            error: function(error) {
                alert('북마크 삭제 중 오류가 발생했습니다.');
                console.error('Error deleting bookmark:', error);
            }
        });
    });
</script>
<%@ include file="module/footer.jsp" %>
</body>