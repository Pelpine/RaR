<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 등록 요청</title>
<script type="text/javascript">
function showPopup() { window.open("/RaR/book/bookForm.do", "a", "width=400, height=300, left=100, top=50");
}
function updateParent(bk_name, author, pubDate, coverUrl, categoryName, price) {
    document.getElementById("bk_name").value = bk_name;
    document.getElementById("author").value = author;
    document.getElementById("pubdate").value = pubDate;
    document.getElementById("coverurl").src = coverUrl;
    document.getElementById("categoryname").value = categoryName;
    document.getElementById("price").value = price;
}
</script>
</head>
<body>
<div>
	<h4>책 등록 요청</h4>
	<form action="book.do" method="post" name="test">
		<ul>
			<li>
				<img src="${books.coverUrl}" id="coverurl">
			</li>
			<li>
				<label for="bk_name">책 이름</label>
				<input type="button" value="책목록" onclick="showPopup();" />
				<input type="text" id="bk_name" name="bk_name" value="${books.bk_name}" readonly>
			</li>
			<li>
				<label for="author">저자</label>
				<input type="text" id="author" name="author" value="${books.author}" readonly>
			</li>
			<li>
				<label for="pubdate">책 등록 날짜</label>
				<input type="text" id="pubdate" name="pubdate" value="${books.pubDate}" readonly>
			</li>
			<li>
				<label for="price">정가</label>
				<input type="number" id="price" name="price" value="${books.price}" readonly>
			</li>
			<li>
				<label for="categoryname">장르</label>
				<input type="text" id="categoryname" name="categoryname" value="${books.categoryName}" readonly>
			</li>
			<li>
				<label for="item_grade">상품상태</label>
				<select name="item_grade">
					<option value="1">좋음</option>
					<option value="2">보통</option>
					<option value="3">좋지않음</option>
				</select>
			</li>
			<li>
				<label for="comment">코맨트</label>
				<input type="text" id="comment" name="comment">
			</li>
		</ul>
		<input type="submit" value="등록요청">
		<input type="button" value="취소" onclick="location.href='/main/main.do'">
	</form>
</div>
</body>
</html>
