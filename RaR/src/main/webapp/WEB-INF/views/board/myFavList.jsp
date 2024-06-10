<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ksh2.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>좋아요 목록</h2>
		<div class="list-space align-right">
		<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
		</div>
		<c:if test="${count==0}">
		<div class="result-display">
		내가 좋아요 누른 게시물이 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count>0}">
		<table class="book-table">
			<tr>
				<th>제목</th>
				<th>작성자 이메일</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="board" items="${list}">
			<tr>
				<td><a href="detail.do?board_num=${board.board_num}">${board.title}</a></td>
				<td>${board.user_email}</td>
				<td>${board.reg_date}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>
















