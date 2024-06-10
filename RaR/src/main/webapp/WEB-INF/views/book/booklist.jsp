<%@page import="kr.rar.dao.ItemDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책 목록</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/psk.css" type="text/css">
</head>
<body>
	<div>
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
	</div>
	<div class="container">
		<div class="sidebar">
			<div class="genre-buttons">
				<jsp:include page="/WEB-INF/views/book/genre.jsp" />
			</div>
		</div>
		<div class="main-content">
			<div class="content-main">
				<div class="list-space buttons-right">
					<input type="button" value="등록요청목록"
						onclick="location.href='${pageContext.request.contextPath}/book/list.do'"> <input type="button"
						value="목록" onclick="location.href='${pageContext.request.contextPath}/book/bookslist.do'"> <input
						type="button" value="홈으로"
						onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
				</div>
				<c:if test="${count == 0}">
					<div class="result-display">표시할 게시물이 없습니다.</div>
					<ul class="search">
						<li><select name="keyfield">
								<option value="1"
									<c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
								<option value="2"
									<c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
								<option value="3"
									<c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
						</select></li>
						<li><input type="search" size="16" name="keyword"
							id="keyword" value="${param.keyword}"></li>
						<li><input type="submit" value="검색"></li>
					</ul>
				</c:if>
				<c:if test="${count > 0 }">
					<div class="book-list">
						<c:forEach var="book" items="${list}">
							<div class="book-item">
								<div class="book-img">
									<a href="booksdetail.do?bk_num=${book.bk_num}"><img src="${book.bk_img}" alt="${book.bk_name}"></a>
								</div>
								<div class="book-details">
									<div class="book-name">
										<a href="booksdetail.do?bk_num=${book.bk_num}">${book.bk_name}</a>
									</div>
									<div class="book-writer">${book.bk_writer}</div>
									<div class="book-publisher">${book.bk_publisher}</div>
									<div class="book-count">현재 판매 매수: ${book.bk_count}</div>
								</div>
								<div class="book-price">
									<div class="original-price">원가: 10000 원</div>
									<div class="discount-price">최저가: 8000 원</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<form action="booklist.do" id="search_form" method="post">
						<ul class="search">
							<li><select name="keyfield">
									<option value="1"
										<c:if test="${param.keyfield==1}">selected</c:if>>책이름</option>
									<option value="2"
										<c:if test="${param.keyfield==2}">selected</c:if>>작가</option>
									<option value="3"
										<c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
							</select></li>
							<li><input type="search" size="16" name="keyword"
								id="keyword" value="${param.keyword}"></li>
							<li><input type="submit" value="검색"></li>
						</ul>
					</form>
					<div class="align-center">${page}</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
