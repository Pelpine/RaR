<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/attendance.js"></script>
<div class="main-nav">
    <div class="page-main2">
     <div class="header-box">
        <div class="top-nav">
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
                                onclick="location.href='${pageContext.request.contextPath}/cart/cartList.do'">장바구니</button>
                        <button class="header_button" 
                                onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">마이페이지</button>
                        <button class="header_button" 
                                onclick="location.href='${pageContext.request.contextPath}/member/logout.do'">로그아웃</button>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="header">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/main/main.do">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Read and Renew">
                </a>
            </div>
            <form action="${pageContext.request.contextPath}/book/booklist.do" id="search_form" method="post" class="search-bar2">
                <input type="hidden" name="keyfield" id="keyfield" value="4">
                <input type="search" size="16" name="keyword" id="keyword" class="search-bar3" placeholder="검색어를 입력하십시오.">
                <button type="submit">🔍</button>
            </form>
            <div class="profile">
                <ul>
                    <c:if test="${!empty user_num && empty user_photo}">
                        <li class="menu-profile">
                            <a href="${pageContext.request.contextPath}/member/myPage.do">
                            <img src="${pageContext.request.contextPath}/images/face.png" class="my-photo">
                            </a>
                            <a>${user_name}님</a>
                        </li>    
                    </c:if>
                    <c:if test="${!empty user_num && !empty user_photo}">
                        <li class="menu-profile">
                            <a href="${pageContext.request.contextPath}/member/myPage.do">
                            <img src="${pageContext.request.contextPath}/upload/${user_photo}" width="48" height="48" class="my-photo">
                            </a>
                            <a>${user_name}님</a>
                    </c:if>
                </ul>
            </div>
        </div>
        </div>
        <div class="menu">
            <div class="dropdown">
                <button class="dropbtn" onclick="location.href='${pageContext.request.contextPath}/book/booklist.do'">
                도서목록
                </button>
            </div>
            <div class="dropdown">
                <button class="dropbtn">게시판</button>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/board/list.do">자유게시판</a> 
                    <a href="${pageContext.request.contextPath}/board/genreList.do">장르 게시판</a>
                </div>
            </div>
            <div class="dropdown">
                <button class="dropbtn" onclick="location.href='${pageContext.request.contextPath}/event/eventList.do'">
                이벤트
                </button>
            </div>
            <div class="dropdown">
                <button class="dropbtn" onclick="location.href='${pageContext.request.contextPath}/book/list.do'">
                판매신청
                </button>
            </div>
            <c:if test="${!empty user_num && user_auth == 9}">
                <div class="dropdown">
                    <button class="dropbtn">관리</button>
                    <div class="dropdown-content">
                        <a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a>
                        <a href="${pageContext.request.contextPath}/item/adminList.do">상품관리</a>
						<a href="${pageContext.request.contextPath}/order/adminOrderList.do">주문관리</a>
                        <a href="${pageContext.request.contextPath}/event/eventApproval.do">상품승인</a>
                        <a href="${pageContext.request.contextPath}/refund/adminRefundList.do">환불관리</a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
