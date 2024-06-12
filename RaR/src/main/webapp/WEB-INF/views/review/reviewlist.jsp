<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ksh2.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>리뷰</h2>
		    <div class="align-right">
				<input type="button" value="목록" onclick="location.href=''">
				<input type="button" value="마이페이지" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
			</div>
	    <c:if test="${count2 == 0}">
	        <div class="result-display1">표시할 리뷰가 없습니다.</div>
	    </c:if>
	    <c:if test="${count2 > 0}">
	        <c:forEach var="re" items="${list2}">
	            <div>별점 : ${re.re_rating}</div>
	            <div>유저 이메일 : ${re.memberVO.user_email}</div>
	            <div>리뷰 : ${re.re_comment}</div>
	            <div>이미지 : <img src="${pageContext.request.contextPath}/upload/${re.re_img}" alt="리뷰 이미지"></div>
	            <div>입력일 : ${re.reg_date}</div>
	            <div>수정일 : ${re.modify_date}</div>
	        </c:forEach>
	        <input type="button" onclick="location.href=''" value="수정">
	        <input type="button" onclick="" value="삭제">
	    </c:if>
	    <div class="align-center1">${page2}</div>
	</div>
</div>
</body>
</html>