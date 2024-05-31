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
				<c:if test="${book.item_grade == 1}"><label>상품 상태 : 좋음</label></c:if>
				<c:if test="${book.item_grade == 2}"><label>상품 상태 : 보통</label></c:if>
				<c:if test="${book.item_grade == 3}"><label>상품 상태 : 별로</label></c:if>
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
						<option value="1" <c:if test="${book.status==1}">selected</c:if>>미승인</option>
						<option value="2" <c:if test="${book.status==2}">selected</c:if>>승인</option>
						<option value="3" <c:if test="${book.status==3}">selected</c:if>>반출</option>
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
				<label>중고책 이미지</label>
				<img alt="" src="${pageContext.request.contextPath}/upload/${book.photo}">
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
			<li>
				<label>isbn : ${book.isbn}</label>
			</li>
		</ul>
		<input type="button" value="목록" onclick="location.href='list.do'">
		<c:if test="${book.status == 1}">
			<input type="button" value="수정" onclick="location.href='updatebook.do'">
			<input type="button" value="삭제" onclick="location.href='deletebook.do'">
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
		</c:if>
		
	</div>
	</form>
</div>
</body>
</html>