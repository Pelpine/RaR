<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, kr.rar.vo.BookVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알라딘 도서 검색 결과</title>
<style>
body {
    font-family: Arial, sans-serif;
}
.book {
    margin-bottom: 20px;
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
}
.book img {
    max-width: 100px;
}
</style>
<script type="text/javascript">
function funtest() {
	var bk_name = document.getElementById("bk_name").value;
	var author = document.getElementById("author").value;
	var pubDate = document.getElementById("pubdate").value;
	var coverUrl = document.getElementById("coverurl").value;
	var categoryName = document.getElementById("categoryname").value;
	
	window.opener.updateParent(bk_name, author, pubDate, coverUrl, categoryName);
 	window.close();
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/book/headera.jsp"/>
<c:forEach var="book" items="${book}">
	<tr>
    	<td>제목 : ${book.bk_name}<br></td>
    	<td>저자 : ${book.author}<br></td>
    	<td>책등록일 : ${book.pubDate}<br></td>
    	<td>장르 : ${book.categoryName}<br></td>
    	<td><img src="${book.coverUrl}"><br></td>
	</tr>
	<form action="#"  method="post" name="shForm" onsubmit="funtest(); return false;">
		<input type="hidden" value="${book.bk_name}" id="bk_name" name="bk_name">
		<input type="hidden" value="${book.author}" id="author" name="author">
		<input type="hidden" value="${book.pubDate}" id="pubdate" name="pubdate">
		<input type="hidden" value="${book.categoryName}" id="categoryname" name="categoryname">
		<input type="hidden" value="${book.coverUrl}" id="coverurl" name="coverUrl">
		<input type="submit" value="전달">
	</form>
</c:forEach>

</body>
</html>
