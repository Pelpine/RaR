<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<div id="main_logo">
	<h1 class="" style="align-text:Left">
		<a href="${pageContext.request.contextPath}/main/main.do">회원제 게시판</a></h1>
		<form action="${pageContext.request.contextPath}/book/booklist.do" id="search_form" method="post">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
</div>
<div id="main_nav">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do">게시판</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/event/eventList.do">이벤트 목록</a>  
		</li>
		<c:if test="${!empty user_num && user_auth == 9}">
		<li>
			<a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}">상품승인</a>
		</li>
		</c:if>
		<c:if test="${!empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
		</li>
		<li>
			<input type="button" onclick="location.href='${pageContext.request.contextPath}/event/attendanceEventAction.do'" value="출석체크">
		</li>
		</c:if>
		<c:if test="${!empty user_num && !empty user_photo}">
		<li	class="menu-profile">
			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
		</li>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
		<li	class="menu-profile">
			<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
		</li>
		</c:if>
		<c:if test="${!empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/cart/cartList.do">장바구니</a>
		</li>
		<li class="menu-logout">
			[<span>${user_id}</span>]
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
		</c:if>
		<c:if test="${empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
		</li>
		</c:if>
		<li>
			<a href="${pageContext.request.contextPath}/book/list.do">검색</a>
			<a href="${pageContext.request.contextPath}/book/booklist.do">책목록</a>
		</li>
	</ul>
</div>
<!-- header 끝 -->








