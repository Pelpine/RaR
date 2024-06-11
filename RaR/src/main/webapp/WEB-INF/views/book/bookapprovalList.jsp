<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록 요청 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookapproval.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var userNum = ${user_num};
    var userauth = ${user_auth};
    
    $("a.private-link").click(function(event){
        var bookUserNum = $(this).data('usernum');
        var privateNum = $(this).data('privatenum');
        
        if(userauth != 9 && userNum != bookUserNum){
            alert('본인만 확인 가능합니다.');
            event.preventDefault();
        }
    });
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="content-main">
			<h2>등록 요청 목록</h2>
			<form action="list.do" id="search_form" method="post"
				class="search-form">
				<div class="form-group">
					<ul class="search search-left">
						<li><select name="keyfield">
								<option value="1"
									<c:if test="${param.keyfield==1}">selected</c:if>>미등록</option>
								<option value="2"
									<c:if test="${param.keyfield==2}">selected</c:if>>등록</option>
								<option value="3"
									<c:if test="${param.keyfield==3}">selected</c:if>>반려</option>
						</select></li>
						<li><input type="hidden" value="" id="keyword" name="keyword">
							<input type="submit" value="검색" class="search-button"></li>
					</ul>
					<div class="list-space buttons-right">
						<input type="button" value="등록"
							onclick="location.href='bookssd.do'"> <input
							type="button" value="목록" onclick="location.href='list.do'">
						<input type="button" value="홈으로"
							onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
					</div>
				</div>
			</form>
			<c:if test="${count == 0}">
				<div class="book-table">표시할 게시물이 없습니다.</div>
			</c:if>
			<c:if test="${count > 0 }">
				<table class="book-table">
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
							<td><c:choose><c:when test="${book.private_num == 1 or book.memberVO.user_num == user_num or user_auth == 9}">
										<a href="bookdetail.do?approval_id=${book.approval_id}"
										class="private-link book-name" data-userauth="${user_auth}"
										data-usernum="${book.memberVO.user_num}"
										data-privatenum="${book.private_num}">${book.bk_name}</a>
									</c:when><c:when test="${book.private_num == 2 and book.memberVO.user_num != user_num}">
										(비공개)
									</c:when>
								</c:choose></td>
							<td><c:if test="${book.status == 1}">미승인</c:if><c:if test="${book.status == 2}">승인</c:if><c:if test="${book.status == 3}">반려</c:if></td>
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
<hr width="100%" noshade="noshade">
</body>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</html>
