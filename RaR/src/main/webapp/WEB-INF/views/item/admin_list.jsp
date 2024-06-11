<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm = document.getElementById('search_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const keyword = document.getElementById('keyword');
		if(keyword.value.trim()==''){
			alert('검색어를 입력하세요');
			keyword.value = '';
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
		<h2>상품관리</h2>
		<form id="search_form" action="adminList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>상품명</option>					
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
			<input type="button" value="목록"
			    onclick="location.href='adminList.do'">
			<input type="button" value="홈으로"
			    onclick="location.href='${pageContext.request.contextPath}/main/main.do'">        
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 상품이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>번호</th>
				<th>상품명</th>
				<th>가격</th>
				<th>등록일</th>
				<th>등급</th>
				<th>상태</th>
			</tr>
			<c:forEach var="item" items="${list}">
			<tr>
				<td>${item.item_num}</td>
				<td><a href="adminModifyForm.do?item_num=${item.item_num}">${item.bk_name}</a></td>
				<td><fmt:formatNumber value="${item.item_price}"/></td>	
				<td>${item.reg_date}</td>
				<td>
					<c:if test="${item.item_grade == 3}">상</c:if>
					<c:if test="${item.item_grade == 2}">중</c:if>
					<c:if test="${item.item_grade == 1}">하</c:if>
				</td>
				<td>
					<c:if test="${item.status == 1}">미표시</c:if>
					<c:if test="${item.status == 2}">표시</c:if>
				</td>
			</tr>				
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>






