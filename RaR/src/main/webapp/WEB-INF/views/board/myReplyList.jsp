<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ksh2.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>작성한 댓글 목록</h2>
		<div class="list-space align-right">
		<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
		</div>
		<c:if test="${count==0}">
		<div class="result-display">
		표시할 댓글이 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count>0}">
		<table class="book-table">
			<tr>
				<th>글 제목</th>
				<th>댓글 내용</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="reply" items="${list}">
			<tr>
				<td><a href="detail.do?board_num=${reply.board_num}">${reply.board.title}</a></td>
				<td>${reply.content}</td>
				<td>${reply.reg_date}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
















