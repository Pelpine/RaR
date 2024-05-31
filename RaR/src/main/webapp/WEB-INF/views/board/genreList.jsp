<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
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
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>장르 목록</h2>
		<div class="list-space align-right">
		<input type="button" value="장르 등록" onclick="location.href='writeGenreForm.do'"
				<c:if test="${user_auth !=9}">disabled="disabled"</c:if>>
		<input type="button" value="장르 목록" onclick="location.href='genreList.do'">
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count==0}">
		<div class="result-display">
		등록된 장르가 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count>0}">
		<table>
			<tr>
				<th>장르</th>
			</tr>
			<c:forEach var="genre" items="${genreList}">
			<tr>
				<td>${genre.bgu_title}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>
















