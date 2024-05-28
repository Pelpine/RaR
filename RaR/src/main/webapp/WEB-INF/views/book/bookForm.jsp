<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,kr.rar.vo.BookApprovalVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(document).on('submit', 'form.shForm', function(event){
        event.preventDefault();

        var bk_name = $(this).find('input[name="bk_name"]').val();
        var author = $(this).find('input[name="author"]').val();
        var pubDate = $(this).find('input[name="pubdate"]').val();
        var coverUrl = $(this).find('input[name="coverurl"]').val();
        var categoryName = $(this).find('input[name="categoryname"]').val();
        var price = $(this).find('input[name="price"]').val();
        
        window.opener.updateParent(bk_name, author, pubDate, coverUrl, categoryName, price);
        window.close();
    });
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/book/headera.jsp"/>
<c:forEach var="book" items="${book}">
    <div class="book">
        <div>제목 : ${book.bk_name}</div>
        <div>저자 : ${book.author}</div>
        <div>책등록일 : ${book.pubDate}</div>
        <div>장르 : ${book.categoryName}</div>
        <div>정가 : ${book.price} 원</div>
        <div><img src="${book.coverUrl}" alt="Book Cover"></div>
        <form action="#" method="post" class="shForm">
            <input type="hidden" value="${book.bk_name}" name="bk_name">
            <input type="hidden" value="${book.price}" name="price">
            <input type="hidden" value="${book.author}" name="author">
            <input type="hidden" value="${book.pubDate}" name="pubdate">
            <input type="hidden" value="${book.categoryName}" name="categoryname">
            <input type="hidden" value="${book.coverUrl}" name="coverurl">
            <input type="submit" value="전달">
        </form>
    </div>
</c:forEach>
</body>
</html>
