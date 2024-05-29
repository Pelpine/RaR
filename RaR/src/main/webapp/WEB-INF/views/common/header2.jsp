<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<div id="main_login">
    <ul>
		<c:if test="${!empty user_num}">
			<li class="menu-logout">
			    <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
			</li>
		</c:if>
		<c:if test="${empty user_num}">
		    <li>
		        <a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
			</li>
			<li>
			    <a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
			</li>
		</c:if>
	</ul>
</div>
<div id="main_logo">
	<h1 class="align-center">
		<a href="${pageContext.request.contextPath}/main/main.do">회원제 게시판</a>
	</h1>
	
</div>
<div id="main_nav">
	<ul class="tag">
		<li><a>게시판</a>
		   <ul class="subtag">
		       <li><a href="${pageContext.request.contextPath}/board/list.do">일반 게시판</a>
		       <li><a href="${pageContext.request.contextPath}/event/eventList.do">이벤트<br>게시판</a></li>
		   </ul>
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
			<input type="button" onclick="location.href='${pageContext.request.contextPath}/event/attendanceEventAction.do'" value="출석체크">
		</li>
		</c:if>
		<c:if test="${!empty user_num && !empty user_photo}">
		<li	class="menu-profile">
		   <a href="${pageContext.request.contextPath}/member/myPage.do">
		      <img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
		   </a> 
		</li>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
		<li	class="menu-profile">
		   <a href="${pageContext.request.contextPath}/member/myPage.do">
		      <img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
		   </a>
		</li>
		</c:if>
		<c:if test="${!empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/cart/cartList.do">장바구니</a>
		</li>
		</c:if>
		<li>
			<a href="${pageContext.request.contextPath}/book/list.do">책 검색</a>
			<a href="${pageContext.request.contextPath}/book/booklist.do">책 목록</a>
		</li>
	</ul>
</div>
<!-- header 끝 -->








