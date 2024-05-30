<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="page_wrap">
	<!-- 머리말 시작 -->
	<header id="main_header">
		<hgroup>
			<h1 class="master-title">
				<a href="${pageContext.request.contextPath}/main/main.do">메인</a>
			</h1>
		</hgroup>
	</header>
	<!-- 머리말 끝 -->
	<!-- 내비게이션 시작 -->
	<nav id="main_nav">
		<!-- 메뉴 시작 -->
		<div class="pull-left">
			<!-- main 메뉴 -->
			<ul class="outer-menu">
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
							src="${pageContext.request.contextPath}/images/face.png"
							width="25" height="25" class="my-photo">
					</a></li>
				</c:if>
				<li class="outer-menu-item"><span class="menu-title">게시판</span>
					<!-- sub 메뉴 -->
					<ul class="inner-menu">
						<li class="inner-menu-item"><a
							href="${pageContext.request.contextPath}/board/list.do">일반
								게시판</a></li>
						<li class="inner-menu-item"><a
							href="${pageContext.request.contextPath}/event/eventList.do">이벤트
								게시판</a></li>
					    <li class="inner-menu-item"><a
							href="${pageContext.request.contextPath}/">장르
								게시판</a></li>
					</ul></li>
				<li class="outer-menu-item"><span class="menu-title">관리자
						전용</span> <!-- sub 메뉴 -->
					<ul class="inner-menu">
						<li class="inner-menu-item"><a href="#">선택자</a></li>
						<li class="inner-menu-item"><a href="#">속성</a></li>
						<li class="inner-menu-item"><a href="#">박스</a></li>
					</ul></li>
				<li class="outer-menu-item"><span class="menu-title">JavaScript</span>
					<!-- sub 메뉴 -->
					<ul class="inner-menu">
						<li class="inner-menu-item"><a href="#">변수</a></li>
						<li class="inner-menu-item"><a href="#">자료형</a></li>
						<li class="inner-menu-item"><a href="#">연산자</a></li>
						<li class="inner-menu-item"><a href="#">제어문</a></li>
						<li class="inner-menu-item"><a href="#">함수</a></li>
						<li class="inner-menu-item"><a href="#">배열</a></li>
					</ul></li>

			</ul>
		</div>
		<!-- 메뉴 끝 -->
		<!-- 검색 시작 -->
		<div class="pull-right">
			<div class="search-bar">
				<form>
					<input type="search" class="input-search"> <input
						type="submit" class="input-search-submit" value="검색">
				</form>
			</div>
		</div>
		<!-- 검색 끝 -->
	</nav>
	<!-- 내비게이션 끝 -->
	<div id="main_login">
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
	<!-- 꼬리말 시작 -->
	<footer id="main_footer">
		<a href="#">HTML5 Programming</a>
	</footer>
	<!-- 꼬리말 끝 -->
</div>