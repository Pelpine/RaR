<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 및 등록요청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookdetail.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<div class="page-main-top">
<h4>등록 요청</h4>
    <form action="bookup.do" id="upform" method="post">
        <div class="image-container">
            <div class="book-img">
                <img alt="책커버" src="${book.cover}">
                <div>책커버</div>
            </div>
            <div class="secondhand-image">
                <c:if test="${not empty book.photo}">
                    <img alt="중고책 이미지" src="${pageContext.request.contextPath}/upload/${book.photo}">
                    <div>중고책 이미지</div>
                </c:if>
                <c:if test="${empty book.photo}">
                </c:if>
            </div>
        </div>
        <div class="book-details">
            <label><h4 class="inline-block">제목</h4> : ${book.bk_name}</label>
            <label><h4 class="inline-block">저자</h4> : ${book.author}</label>
            <label><h4 class="inline-block">상품 상태</h4> : 
                <c:if test="${book.item_grade == 1}">상</c:if>
                <c:if test="${book.item_grade == 2}">중</c:if>
                <c:if test="${book.item_grade == 3}">하</c:if>
            </label>
            <label><h4 class="inline-block">유저</h4> : ${book.memberVO.user_email}</label>
            <label><h4 class="inline-block">정가</h4> : ${book.price}</label>
            <label><h4 class="inline-block">승인상태</h4> : 
                <c:if test="${book.status == 1}">미승인</c:if>
                <c:if test="${book.status == 2}">승인</c:if> 
                <c:if test="${book.status == 3}">반려</c:if> 
                <c:if test="${book.status == 1 and user_auth == 9}">
                    <select name="status" class="select-status">
                        <option value="1" <c:if test="${book.status==1}">selected</c:if>>미승인</option>
                        <option value="2" <c:if test="${book.status==2}">selected</c:if>>승인</option>
                        <option value="3" <c:if test="${book.status==3}">selected</c:if>>반출</option>
                    </select>
                </c:if>
            </label>
            <label><h4 class="inline-block">장르</h4> : ${book.categoryName}</label>
            <label><h4 class="inline-block">출판사</h4> : ${book.publisher}</label>
            <label><h4 class="inline-block">isbn</h4> : ${book.isbn}</label>
            <label><h4 class="inline-block">코멘트</h4></label>
            <div class="comment-box">${book.ad_comment}</div>
            <label><h4 class="inline-block">요청 날짜</h4> : ${book.request_at}</label>
            <label><h4 class="inline-block">마지막 승인 수정 날짜</h4> : ${book.approved_at}</label>
        </div>
        <div class="action-buttons">
            <input type="button" value="목록" onclick="location.href='list.do'">
            <c:if test="${book.status == 1}">
                <input type="button" value="수정" onclick="location.href='updatebook.do?approval_id=${book.approval_id}&user_email=${book.memberVO.user_email}'">
                <input type="button" value="삭제" onclick="location.href='deletebook.do?approval_id=${book.approval_id}&user_email=${book.memberVO.user_email}'">
            </c:if>
            <c:if test="${user_auth == 9}">
                <c:if test="${book.status == 1}">
                    <input type="submit" value="등록">
                </c:if>
                <input type="hidden" value="${book.approval_id}" name="approval_id">
                <input type="hidden" value="${user_auth}" name="user_auth">
                <input type="hidden" value="${book.bk_name}" name="bk_name">
                <input type="hidden" value="${book.author}" name="author">
                <input type="hidden" value="${book.price}" name="price">
                <input type="hidden" value="${book.categoryName}" name="categoryName">
                <input type="hidden" value="${book.cover}" name="cover">
                <input type="hidden" value="${book.publisher}" name="publisher">
                <input type="hidden" value="${book.photo}" name="photo">
                <input type="hidden" value="${book.isbn}" name="isbn">
                <input type="hidden" value="${book.description}" name="description">
                <input type="hidden" value="${book.item_grade}" name="item_grade">
                <input type="hidden" value="${book.memberVO.user_email}" name="user_email">
                <input type="hidden" value="${book.pubDate}" name="bk_pubdate">
            </c:if>
        </div>
    </form>
</div>
</body>
</html>
