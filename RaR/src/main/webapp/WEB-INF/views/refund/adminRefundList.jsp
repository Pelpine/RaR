<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환불 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="page-main">
	<div class="content-main">
		<h2>환불 목록</h2>
		<div class="align-right">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 환불내역이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table id="order_list">
			<tr>
				<th>환불번호</th>
				<th>유저번호</th>
				<th>상품명</th>
				<th>환불액</th>
				<th>신청일</th>
				<th>환불일</th>
				<th>환불상태</th>
			</tr>
			<c:forEach var="refund" items="${list}" varStatus="status">
			<tr>
				<td>${refund.refund_num}</td>
				<td>${refund.user_num}</td>
				<td><a href="adminRefundDetail.do?refund_num=${refund.refund_num}">${itemList[status.index]}</a></td>
				<td><fmt:formatNumber value="${refund.refund_price}"/>원</td>
				<td>${refund.request_date}</td>
				<td>
					<c:if test= "${!empty refund.refund_date}">${refund.refund_date}</c:if>
					<c:if test= "${empty refund.refund_date}">환불 진행중</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${refund.status == 1}">환불신청</c:when>
						<c:when test="${refund.status == 2}">상품검수대기</c:when>
						<c:when test="${refund.status == 3}">상품검수중</c:when>
						<c:when test="${refund.status == 4}">환불 불가</c:when>
						<c:when test="${refund.status == 5}">환불 완료</c:when>
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</table>	
		<div class="pageNum">${page}</div>
		</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
