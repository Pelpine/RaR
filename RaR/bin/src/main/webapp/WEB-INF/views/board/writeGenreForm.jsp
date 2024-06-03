<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
window.onload = function() {
    const myForm = document.getElementById('write_form');
    // 이벤트 연결
    myForm.onsubmit = function() {
        const title = document.getElementById('title');
        if (title.value.trim() == '') {
            alert('제목을 입력하세요');
            title.value = '';
            title.focus();
            return false;
        }
    };
};
</script>
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h2>글쓰기</h2>
        <form id="write_form" action="writeGenre.do" method="post" enctype="multipart/form-data">
            <ul>
                <li>
                    <label for="title">제목</label>
                    <input type="text" name="bg_title" id="title" maxlength="50">
                </li>
            </ul>
            <div class="align-center">
                <input type="submit" value="등록">
                <input type="button" value="목록" onclick="location.href='genreList.do'">
            </div>
        </form>
    </div>
</div>
</body>
</html>
