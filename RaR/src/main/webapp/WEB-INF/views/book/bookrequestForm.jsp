<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 등록 요청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookform.css">
<script type="text/javascript">
function showPopup() { 
    window.open("/RaR/book/bookForm.do", "a", "width=600, height=800, left=200, top=100");
}

function updateParent(bk_name, author, pubDate, cover, categoryName, price, publisher, isbn, description) {
    document.getElementById("bk_name").value = bk_name;
    document.getElementById("author").value = author;
    document.getElementById("pubdate").value = pubDate;
    document.getElementById("cover").src = cover;
    document.getElementById("coverUrl").value = cover;
    document.getElementById("categoryname").value = categoryName;
    document.getElementById("price").value = price;
    document.getElementById("publisher").value = publisher;
    document.getElementById("isbn").value = isbn;
    document.getElementById("description").value = description;
}

window.onload = function() {
    document.getElementById('test').onsubmit = function() {
        if (document.getElementById('bk_name').value.trim() == '') {
            alert('책 선택은 필수 입니다.');
            document.getElementById('bk_name').value = '';
            document.getElementById('bk_name').focus();
            return false;
        }
    }
}
</script>
</head>
<body>
<div>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
</div>
<div class="page-main">
    <h4 class="left-align">책 등록 요청</h4>
    <form action="book.do" method="post" name="test" enctype="multipart/form-data" id="test" class="form-container">
        <ul class="form-list">
            <li class="form-item">
                <img src="${books.cover}" id="cover">
                <input type="hidden" value="${books.cover}" name="cover" id="coverUrl" >
            </li>
            <li class="form-item form-item-inline">
                <label for="bk_name">책 이름:</label>
                <input type="text" id="bk_name" name="bk_name" value="${books.bk_name}" readonly>
                <input type="button" value="책목록" onclick="showPopup();" />
            </li>
            <li class="form-item form-item-inline">
                <label for="author">저자:</label>
                <input type="text" id="author" name="author" value="${books.author}" readonly>
            </li>
            <li class="form-item form-item-inline">
                <label for="pubdate">출간일:</label>
                <input type="text" id="pubdate" name="pubdate" value="${books.pubDate}" readonly>
            </li>
            <li class="form-item form-item-inline">
                <label for="price">정가:</label>
                <input type="number" id="price" name="price" value="${books.price}" readonly>
            </li>
            <li class="form-item form-item-inline">
                <label for="categoryname">장르:</label>
                <input type="text" id="categoryname" name="categoryname" value="${books.categoryName}" readonly>
            </li>
            <li class="form-item form-item-inline">
                <label for="publisher">출판사:</label>
                <input type="text" id="publisher" name="publisher" value="${books.publisher}" readonly>
            </li>
            <li class="form-item">
                <div>소개 :</div>
                <textarea rows="5" cols="30" name="description" id="description" readonly>${books.description}</textarea>
            </li>
            <li class="form-item form-item-inline">
                <label for="item_grade">상품상태:</label>
                <select name="item_grade" id="item_grade">
                    <option value="1">상</option>
                    <option value="2">중</option>
                    <option value="3">하</option>
                </select>
            </li>
            <li class="form-item form-item-inline">
                <label for="photo">상품사진:</label>
                <input type="file" name="photo" class="input-check" id="photo" accept="image/gif,image/png,image/jpeg">
            </li>
            <li class="form-item">
                <div>코멘트 :</div>
                <textarea rows="5" cols="30" id="comment" name="comment"></textarea>
            </li>
            <li class="form-item form-item-inline inline-group">
                <label for="private_num">공개 비공개 설정(기본 : 공개설정):</label>
                <input type="radio" name="private_num" value="1" id="private_num1" checked>공개
                <input type="radio" name="private_num" value="2" id="private_num2">비공개
            </li>
        </ul>
        <input type="hidden" value="${books.isbn}" name="isbn" id="isbn">
        <div class="button-group">
            <input type="submit" value="등록요청">
            <input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
        </div>
    </form>
</div>
</body>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</html>
