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
</head>
<body>
<jsp:include page="/WEB-INF/views/book/headera.jsp"/>
<c:forEach var="book" items="${book}">
	<tr>
    	<td>제목 : ${book.title}<br></td>
    	<td>저자 : ${book.author}<br></td>
    	<td>책등록일 : ${book.pubDate}<br></td>
    	<td><img src="${book.coverUrl}"><br></td>
	</tr>
</c:forEach>
</body>
</html>
