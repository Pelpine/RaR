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
	}
}
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>작성한 댓글</h2>
		<form id="search-form" action="myPostingList.do" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1" <c:if test ="${param.keyfield==1}">selected</c:if>>글 제목</option>
					<option value="2" <c:if test ="${param.keyfield==2}">selected</c:if>>내용</option>
				</select>
			</li>
			<li>
				<input type="search" size="16" name="keyword"
						id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="검색">
			</li>
		</ul>
		</form>
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
				<th>게시글 번호</th>
				<th>글 제목</th>
				<th>댓글 내용</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="reply" items="${list}">
			<tr>
				<td>${reply.board_num}</td>
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
</body>
</html>
















