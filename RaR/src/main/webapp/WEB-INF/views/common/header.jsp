<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main_nav">
	<div class="header">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="Read and Renew">
        </div>
        <div class="search-bar">
            <input type="text" placeholder="검색어를 입력하십시오.">
            <button type="submit">🔍</button>
        </div>
        <div class="auth-buttons">
            <c:if test="${empty user_num}">
                <div class="header_button_group">
                    <button class="header_button" 
                            onclick="location.href='${pageContext.request.contextPath}/member/registerUserForm.do'">회원가입</button>
                    <button class="header_button" 
                            onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">로그인</button>
                </div>
            </c:if>
            <c:if test="${!empty user_num}">
                <div class="header_button_group">
                    <button class="header_button"
                            onclick="location.href='${pageContext.request.contextPath}/event/attendanceEventAction.do'">출석체크</button>
                    <button class="header_button" 
                            onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">마이페이지</button>
                    <button class="header_button" 
                            onclick="location.href='${pageContext.request.contextPath}/member/logout.do'">로그아웃</button>
                </div>
            </c:if>
        </div>
		<ul>
            <c:if test="${!empty user_num && empty user_photo}">
                <li class="menu-profile">
                    <img src="${pageContext.request.contextPath}/images/face.png" class="my-photo">
                </li>
                <li>
                    [<span>${user_email}</span>]
                </li>
            </c:if>
            <c:if test="${!empty user_num && !empty user_photo}">
                <li class="menu-profile">
                    <img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
                </li>
                <li>
                    [<span>${user_email}</span>]
                </li>
            </c:if>
        </ul>
    </div>
    <div class="menu">
        <div class="dropdown">
            <button class="dropbtn">SHOP</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/cart/cartList.do">장바구니</a>
                <a href="${pageContext.request.contextPath}/book/list.do">책 검색</a>
                <a href="${pageContext.request.contextPath}/book/booklist.do">책 목록</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">장르</button>
            <div class="dropdown-content">
                <a href="#">스릴러</a>
                <a href="#">SF 소설</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">게시판</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/board/list.do">자유게시판</a>
                <a href="#">QnA 게시판</a>
            </div>
        </div>
        <div class="dropdown">
            <button class="dropbtn">이벤트</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/event/eventList.do">1 이벤트</a>
                <a href="#">2 이벤트</a>
            </div>
        </div>
        <c:if test="${!empty user_num && user_auth == 9}">
            <div class="dropdown">
                <button class="dropbtn">관리</button>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a>
                    <a href="${pageContext.request.contextPath}/event/eventApproval.do">상품승인</a>
                </div>
            </div>
        </c:if>
    </div>
</div>