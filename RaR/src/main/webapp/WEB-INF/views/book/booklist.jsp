<%@page import="kr.rar.dao.ItemDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<div class="list-space align-right">
				<input type="button" value="등록요청목록" onclick="location.href='list.do'">
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
					<th></th>
					<th>책이름</th>
					<th>작가</th>
					<th>출판사</th>
					<th>현제 판매 매수</th>
				</tr>
				<c:forEach var="book" items="${list}">
				<tr>
					<td><img src="${book.bk_img}"></td>
					<td><a href="booksdetail.do?bk_num=${book.bk_num}">${book.bk_name}</a></td>
					<td>${book.bk_writer}</td>
					<td>${book.bk_publisher}</td>
					<td>${count1}</td>
				</tr>
				</c:forEach>
			</table>
			<form action="booklist.do" id="search_form" method="post">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
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
			<div class="align-center">${page}</div>
			</c:if>
		</div>
</div>
</body>
</html>