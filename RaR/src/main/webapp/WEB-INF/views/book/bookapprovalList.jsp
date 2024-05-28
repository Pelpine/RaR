<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2>등록 요청 목록</h2>
			<form action="list.do" id="search_form" method="post">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>미등록</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>등록</option>
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
			<div class="list-space align-right">
				<input type="button" value="등록" onclick="location.href='bookssd.do'">
				<input type="button" value="목록" onclick="location.href='list.do'">
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count == 0}">
				<div class="result-display">
				표시할 게시물이 없습니다.
				</div>
			</c:if>
			<c:if test="${count > 0 }">
			<table>
				<tr>
					<th>요청 번호</th>
					<th>책이름</th>
					<th>승인상태</th>
					<th>승인요청날짜</th>
					<th>승인날짜</th>
					<th>승인요청아이디</th>
				</tr>
				<c:forEach var="book" items="${list}">
				<tr>
					<td>${book.approval_id}</td>
					<td><a href="detail.do?board_num=${board.approval_id}">${book.bk_name}</a></td>
					<td>${book.status}</td>
					<td>${book.request_at}</td>
					<td>${book.approved_at}</td>
					<td>${book.memberVO.user_email}</td>
				</tr>
				</c:forEach>
			</table>
			<div class="align-center">${page}</div>
			</c:if>
		</div>
</div>
</body>
</html>