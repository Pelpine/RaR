<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jsy2.css" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/board.reply2.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	
	<div class="content-main">
		<div class="post-content">
			<h2>${board_genre.bg_title}</h2>
			<ul class="detail-info">
				<li>
					<c:if test="${!empty board.user_photo}">
					<img src="${pageContext.request.contextPath}/upload/${board.user_photo}"
							width="40" height="40" class="my-photo">
					</c:if>
					<c:if test="${empty board.user_photo}">
					<img src="${pageContext.request.contextPath}/images/face.png"
					width ="40" height="40" class="my-photo">
					</c:if>
				</li>
				<li class="info-item">
					<span class="email">${board.user_email}</span><br>
					<span>장르: ${genre.bg_title}</span>
				</li>
			</ul>
			<hr class="section-divider">
			<p>
				의견을 자유롭게 표현해주세요
			</p>
			<hr class="section-divider">
			<ul class="detail-sub">
				<li>
    <%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
    <%
        Integer user_auth = (Integer) session.getAttribute("user_auth");
        Integer user_num = (Integer) session.getAttribute("user_num");
    %>
    <c:if test="${user_num == genre.user_num}">
    <input type="button" value="수정" onclick="location.href='updateGenreForm.do?bg_num=${genre.bg_num}'">
	</c:if>   
    <c:if test="${user_num == genre.user_num || user_auth==9 }">
        <input type="button" value="삭제" id="delete_btn">
        <script type="text/javascript">
            const delete_btn = document.getElementById('delete_btn');
            //이벤트 연결
            delete_btn.onclick = function() {
                let choice = confirm('삭제하시겠습니까?');
                if (choice) {
                    location.replace('deleteGenre.do?bg_num=${genre.bg_num}');
                }
            };
        </script>
    </c:if>
</li>
			</ul>
		</div>
		<!-- 댓글 시작 -->
		<div id="reply_div">
			<span class="re-title"></span>
			<form id="re_form">
				<input type="hidden" name="bg_num" value="${genre.bg_num}" id="board_num">
				<textarea rows="3" cols="50" name="content"
				<c:if test="${empty user_num}">disabled="disabled"</c:if> id="re_content"
				class="content"><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
				<div id="re_first" class="flex-container">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송" class="submit-button">
				</div>
				</c:if>
			</form>
		</div>
		<!-- 댓글 목록 출력 시작 -->
		<div id="output" class="comment-list"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음 글 보기" class="next-button">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50" class="loading-gif">
		<!-- 출력끝  -->
		<!-- 댓글 끝 -->
	</div>		
</div>
</div>
</body>
</html>