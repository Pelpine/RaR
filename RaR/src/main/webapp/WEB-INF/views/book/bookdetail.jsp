<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 및 등록요청</title>
</head>
<body>
<div>
<form action="bookup.do" id="upform" method="post">
	<div>
		<img alt="a" src="${book.cover}">
	</div>
	<div>
		<ul>
			<li>
				<label>제목 : ${book.bk_name}</label>
			</li>
			<li>
				<label>저자 : ${book.author}</label>
			</li>
			<li>
				<label>상품 상태 : ${book.item_grade}</label>
			</li>
			<li>
				<label>유저 : ${book.memberVO.user_email}</label>
			</li>
			<li>
				<label>정가 : ${book.price}</label>
			</li>
			<li>
				<label>승인상태 : ${book.status}</label>
				<select name="status">
					<c:if test="${user_auth == 9}">
					<option value="1">미승인</option>
					<option value="2">승인</option>
					<option value="3">반출</option>
					</c:if>
				</select>
			
			</li>
			<li>
				<label>장르 : ${book.categoryName}</label>
			</li>
			<li>
				<label>출판사 : ${book.publisher}</label>
			</li>
			<li>
				<label>출간일 : ${book.pubDate}</label>
			</li>
			<li>
				<label>코맨트 : ${book.ad_comment}</label>
			</li>
			<li>
				<label>요청 날짜 : ${book.request_at}</label>
			</li>
			<li>
				<label>마지막 승인 수정 날짜 : ${book.approved_at}</label>
			</li>
		</ul>
		<input type="button" value="수정" onclick="location.href='updatebook.do'">
		<input type="button" value="목록" onclick="location.href='list.do'">
		<c:if test="${user_auth == 9}">
		<input type="hidden" value="${book.approval_id}" name="approval_id">
		<input type="hidden" value="${user_auth}" name="user_auth">
		<input type="hidden" value="${book.bk_name}" name="bk_name">
		<input type="hidden" value="${book.author}" name="author">
		<input type="hidden" value="${book.price}" name="price">
		<input type="hidden" value="${book.categoryName}" name="categoryName">
		<input type="hidden" value="${book.cover}" name="cover">
		<input type="hidden" value="${book.publisher}" name="publisher">
		<input type="submit" value="등록">
		</c:if>
		
	</div>
	</form>
</div>
</body>
</html>