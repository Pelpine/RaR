<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="main_logo">
	<h1 class="align-center">
		<a href="${pageContext.request.contextPath}/main/main.do">MAIN</a>
	</h1>
</div>
<div class="search-bar">
	<input type="text" placeholder="검색어를 입력하십시오.">
	<button type="submit">🔍</button>
</div>
<!-- 머리말 끝 -->
<!-- 내비게이션 시작 -->
<!-- 메뉴 시작 -->
<div id=main_login>
	<ul>
		<c:if test="${!empty user_num}">
			<li class="menu-logout"><a
				href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
			</li>
		</c:if>
		<c:if test="${empty user_num}">
			<li><a
				href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
			</li>
			<li><a
				href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
			</li>
		</c:if>
	</ul>
</div>
<!-- main 메뉴 -->
<div class="pull-right">
	<ul class="righter-in">
		<c:if test="${!empty user_num && !empty user_photo}">
			<li class="menu-profile"><a
				href="${pageContext.request.contextPath}/member/myPage.do"> <img
					src="${pageContext.request.contextPath}/upload/${user_photo}"
					width="25" height="25" class="my-photo">
			</a></li>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
			<li class="menu-profile"><a
				href="${pageContext.request.contextPath}/member/myPage.do"> <img
					src="${pageContext.request.contextPath}/images/face.png" width="25"
					height="25" class="my-photo">
			</a></li>
		</c:if>
	</ul>
</div>
<div class="pull-left">
	<ul class="outer-menu">
		<li class="outer-menu-item"><span class="menu-title">게시판</span> <!-- sub 메뉴 -->
			<ul class="inner-menu">
				<li class="inner-menu-item"><a
					href="${pageContext.request.contextPath}/board/list.do">일반 게시판</a>
				</li>
				<li class="inner-menu-item"><a
					href="${pageContext.request.contextPath}/event/eventList.do">이벤트
						게시판</a></li>
				<li class="inner-menu-item"><a
					href="${pageContext.request.contextPath}/board/genreList.do">장르
						게시판</a></li>
			</ul></li>
		<li class="outer-menu-item"><span class="menu-title">책 전용</span>
			<!-- sub 메뉴 -->
			<ul class="inner-menu">
				<c:if test="${!empty user_num}">
					<li class="inner-menu-item"><a
						href="${pageContext.request.contextPath}/cart/cartList.do">장바구니</a>
					</li>
				</c:if>
				<li class="inner-menu-item"><a
					href="${pageContext.request.contextPath}/book/list.do">책 검색</a></li>
				<li class="inner-menu-item"><a
					href="${pageContext.request.contextPath}/book/booklist.do">책 목록</a></li>
			</ul></li>
		<li class="outer-menu-item"><span class="menu-title">관리자</span>
			<!-- sub 메뉴 -->
			<ul class="inner-menu">
				<li class="inner-menu-item">
				<a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a>
				</li>
			</ul></li>

	</ul>
</div>
<!-- 내비게이션 끝 -->