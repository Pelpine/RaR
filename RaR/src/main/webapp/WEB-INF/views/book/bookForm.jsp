<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,kr.rar.vo.BookApprovalVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>알라딘 도서 검색 결과</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/book.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(document).on('submit', 'form.shForm', function(event){
        event.preventDefault();

        var bk_name = $(this).find('input[name="bk_name"]').val();
        var author = $(this).find('input[name="author"]').val();
        var pubDate = $(this).find('input[name="pubdate"]').val();
        var cover = $(this).find('input[name="cover"]').val();
        var categoryName = $(this).find('input[name="categoryname"]').val();
        var price = $(this).find('input[name="price"]').val();
        var publisher = $(this).find('input[name="publisher"]').val();
        var isbn = $(this).find('input[name="isbn"]').val();
        var description = $(this).find('input[name="description"]').val();
        
        window.opener.updateParent(bk_name, author, pubDate, cover, categoryName, price, publisher, isbn, description);
        window.close();
    });
});
</script>
</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/book/headera.jsp"/>
</div>
<div class="book-list">
<c:forEach var="book" items="${book}">
    <div class="book">
        <div class="book-details">
        	<div><img src="${book.cover}" alt="Book Cover"></div>
            <div>제목 : ${book.bk_name}</div>
            <div>저자 : ${book.author}</div>
            <div>출간일 : ${book.pubDate}</div>
            <div>장르 : ${book.categoryName}</div>
            <div>정가 : ${book.price} 원</div>
            <div>출판사 : ${book.publisher}</div>
            <div>isbn : ${book.isbn}</div>
            <div>설명 : ${book.description}</div>
        </div>
        <form action="#" method="post" class="shForm">
            <input type="hidden" value="${book.bk_name}" name="bk_name">
            <input type="hidden" value="${book.price}" name="price">
            <input type="hidden" value="${book.author}" name="author">
            <input type="hidden" value="${book.pubDate}" name="pubdate">
            <input type="hidden" value="${book.categoryName}" name="categoryname">
            <input type="hidden" value="${book.cover}" name="cover">
            <input type="hidden" value="${book.publisher}" name="publisher">
            <input type="hidden" value="${book.isbn}" name="isbn">
            <input type="hidden" value="${book.description}" name="description">
            <input type="submit" value="책 선택" class="submit-button">
        </form>
    </div>
</c:forEach>
</div>
</body>
</html>
