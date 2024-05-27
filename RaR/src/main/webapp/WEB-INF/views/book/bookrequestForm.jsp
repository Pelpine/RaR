<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 등록 요청</title>
<script type="text/javascript">
function showPopup() { window.open("../book/bookForm.do", "a", "width=400, height=300, left=100, top=50"); }
</script>
</head>
<body>
<div>
	<h4>책 등록 요청</h4>
	<form action="">
		<ul>
			<li>
				<label for="bk_name">책 이름</label><input type="button" value="책목록" onclick="showPopup();" />
				<input type="text" id="bk_name" name="bk_name" value="${books.bk_name}">
			</li>
			<li>
				<label for="author">저자</label>
				<input type="text" id="author" name="author" value="${books.author}">
			</li>
			<li>
				<img src="${books.coverUrl}">
				<input type="text" id="bk_name" name="bk_name">
			</li>
			<li>
				<label for="categoryName">장르</label>
				<input type="text" id="categoryName" name="categoryName" value="${books.categoryName}">
			</li>
			<li>
				<label for="bk_name">상품상태</label>
				<input type="text" id="bk_name" name="bk_name">
			</li>
			<li>
				<label for="bk_name">코맨트</label>
				<input type="text" id="bk_name" name="bk_name">
			</li>
		</ul>
		<input type="submit" value="등록요청">
		<input type="button" value="취소" onclick="location.href='/main/main.do'">
	</form>
</div>
</body>
</html>