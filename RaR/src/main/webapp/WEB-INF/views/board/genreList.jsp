<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jsy.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm=document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const keyword = document.getElementById('keyword');
		
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요');
			keyword.value='';
			keyword.focus();
			return false;
		}
	};
};
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">

		<div class="content-main">
			<h2>장르 게시판</h2>
			<form id="search_form" action="genreList.do" method="get">
				<ul class="search">
					<li><input type="search" size="50" name="keyword" id="keyword"
						value="${param.keyword}"></li>
					<li><input type="submit" value="검색"></li>
				</ul>
			</form>
			<div class="list-space align-right">
				<input type="button" value="장르 쓰기"
					onclick="location.href='writeGenreForm.do'"
					<c:if test="${user_auth !=9}">disabled="disabled"</c:if>> <input
					type="button" value="홈으로"
					onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count==0}">
				<div class="result-display">표시할 게시물이 없습니다.</div>
			</c:if>
			<c:if test="${count>0}">
				<table class="genre-table">
					<thead>
						<tr>
							<th>게시글 번호</th>
							<th>제목</th>
						</tr>
					</thead>
					<tbody>


						<c:forEach var="genre" items="${list}">
							<!-- 구분선 추가 -->


							<tr>
								<td>${genre.bg_num}</td>
								<td><a href="detailGenre.do?bg_num=${genre.bg_num}">${genre.bg_title}</a></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
				<div class="align-center">${page}</div>
			</c:if>
		</div>
	</div>
</body>
</html>
















