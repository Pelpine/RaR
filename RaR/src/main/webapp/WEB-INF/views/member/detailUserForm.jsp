<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${member.user_email}의 회원정보(관리자 전용)</h2>
		<form action="adminUser.do" method="post"
		                                id="detail_form">
			<input type="hidden" name="user_num" 
			                     value="${member.user_num}">
			<ul>
				<li>
					<label>등급</label>
					<c:if test="${member.user_auth!=9}">
					<input type="radio" name="user_auth" value="1" id="user_auth1" <c:if test="${member.user_auth == 1}">checked</c:if>>정지
					<input type="radio" name="user_auth" value="2" id="user_auth2" <c:if test="${member.user_auth == 2}">checked</c:if>>일반
					</c:if>
					<c:if test="${member.user_auth==9}">
					<input type="radio" name="user_auth" value="9" id="user_auth3" checked>관리
					</c:if>
				</li>
			</ul> 
			<ul>
				<li>
					<label>이름</label>${member.user_name}
				</li>
				<li>
				<c:if test="${member.user_auth == 1}">
					<label>회원등급</label> 정지
				</c:if>
				<c:if test="${member.user_auth == 2}">
					<label>회원등급</label> 일반
				</c:if>
				<c:if test="${member.user_auth == 3}">
					<label>회원등급</label> 실버
				</c:if>
				<c:if test="${member.user_auth == 4}">
					<label>회원등급</label> 골드
				</c:if>
				<c:if test="${member.user_auth == 5}">
					<label>회원등급</label> 플레티넘
				</c:if>
				<c:if test="${member.user_auth == 9}">
					<label>회원등급</label> 관리자
				</c:if>
				</li>
				<li>
					<label>전화번호</label>${member.user_phone}
				</li>
				<li>
					<label>이메일</label>${member.user_email}
				</li>
				<li>
					<label>우편번호</label>${member.user_zipcode}
				</li>
				<li>
					<label>주소</label>${member.user_address1} ${member.user_address2}
				</li>
				<li>
					<label>가입일</label>${member.user_date}
				</li>
				<li>
				<c:choose>
				<c:when test="${empty member.user_point}">
						<label>포인트</label> 0
				</c:when>
				<c:when test="${!empty member.user_point}">
						<label>포인트</label> ${member.user_point}
				</c:when>
				</c:choose>
				</li>
				<li>
					<label>포인트 지급</label>
					<input type="number" name="user_point" value="0" id="user_point">
				</li>
				<li>
					<label for="user_comment">관리자 코멘트</label>
					<textarea rows="5" cols="40"
					  name="user_comment" id="user_comment">${member.user_comment}</textarea>
				</li>
			</ul>
			<div class="align-center">
				<c:if test="${member.user_auth!=9}">
				<input type="submit" value="수정">
				</c:if>
				<input type="button" value="목록"
				     onclick="location.href='adminList.do'">
			</div>                                                   
		</form>
	</div>
</div>
</body>
</html>





