<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	src="${pageContext.request.contextPath}/js/board.fav.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">

		<div class="content-main">
			<div class="post-content">
				<h2>${board.title}</h2>
				<ul class="detail-info">
					<li><c:choose>
							<c:when test="${not empty board.user_photo}">
								<img
									src="${pageContext.request.contextPath}/upload/${board.user_photo}"
									width="40" height="40" class="my-photo">
							</c:when>
							<c:otherwise>
								<img src="${pageContext.request.contextPath}/images/face.png"
									width="40" height="40" class="my-photo">
							</c:otherwise>
						</c:choose></li>
					<li class="info-item"><span class="email">작성자:
							${board.user_email}</span> <span class="hit">조회: ${board.hit}</span></li>
				</ul>
				<hr class="section-divider">
				<c:if test="${not empty board.filename}">
					<div class="align-center">
						<img
							src="${pageContext.request.contextPath}/upload/${board.filename}"
							class="detail-img">
					</div>
				</c:if>
				<p>${board.content}</p>
				<hr class="section-divider">
				<ul class="detail-sub">
					<li><img id="output_fav" data-num="${board.board_num}"
						src="${pageContext.request.contextPath}/images/favoff.png"
						width="50"> <span id="output_fcount"></span> 좋아요</li>
					<li><c:choose>
							<c:when test="${not empty board.modify_date}">
                        최근 수정일: ${board.modify_date}
                    </c:when>
							<c:otherwise>
                        작성일: ${board.reg_date}
                    </c:otherwise>
						</c:choose> <c:if test="${user_num == board.user_num}">
							<input type="button" value="수정"
								onclick="location.href='updateForm.do?board_num=${board.board_num}'">
							<input type="button" value="삭제" id="delete_btn">
							<script type="text/javascript">
								const delete_btn = document
										.getElementById('delete_btn');
								delete_btn.onclick = function() {
									let choice = confirm('삭제하시겠습니까?');
									if (choice) {
										location
												.replace('delete.do?board_num=${board.board_num}');
									}
								};
							</script>
						</c:if></li>
				</ul>
			</div>
			<!-- 댓글 시작 -->
			<div id="reply_div">
				<form id="re_form">
					<input type="hidden" name="board_num" value="${board.board_num}"
						id="board_num">
					<textarea rows="3" name="content"
						<c:if test="${empty user_num}">disabled="disabled"</c:if>
						id="re_content" class="content"><c:if
							test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
					<c:if test="${!empty user_num}">
						<div id="re_first" class="flex-container">
							<div class="letter-count">300/300</div>
						</div>
						<div id="re_second" class="align-right">
							<input type="submit" value="전송" class="submit-button">
						</div>
					</c:if>
				</form>
			</div>
			<!-- 댓글 목록 출력 시작 -->
			<div id="output" class="comment-list"></div>
			<div class="paging-button" style="display: none;">
				<input type="button" value="다음 글 보기" class="next-button">
			</div>
			<div id="loading" style="display: none;">
				<img src="${pageContext.request.contextPath}/images/loading.gif"
					width="50" height="50" class="loading-gif">
			</div>
		</div>
	</div>
</body>
</html>