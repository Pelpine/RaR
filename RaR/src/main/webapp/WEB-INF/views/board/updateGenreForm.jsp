<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jsy3.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm = document.getElementById('update_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const title = doucument.getElementById('bg_title');
		if(title.value.trim()==''){
			alert('제목을 입력하세요');
			title.value='';
			title.focus();
			return false;
		}
	}
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>글 수정</h2>
		<form id="update_form" action="updateGenre.do" method="post"
							enctype="multipart/form-data">
		<input type="hidden" name="bg_num" value="${genre.bg_num}">
			<ul>
				<li>
					<label for="bg_title">장르</label>
					<input type="text" name="bg_title" id="bg_title"
								value="${genre.bg_title}" maxlength="50">
				</li>
				
				
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="목록"
					onclick="location.href='genreList.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>