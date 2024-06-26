<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 목록(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
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
<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
<div class="page-main">
	<div class="content-main">
		<h2>주문 목록(관리자 전용)</h2>
		<form id="search_form" action="adminOrderList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>주문번호</option>
						<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>회원이메일</option>
						<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>상품명</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name = "keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<div class="align-right">
			<input type="button" value="목록" onclick="location.href='adminOrderList.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 주문내역이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table id="admin_order_list">
			<tr>
				<th>주문번호</th>
				<th>구매자 이메일</th>
				<th>상품명</th>
				<th>총결제금액</th>
				<th>주문일</th>
				<th>주문상태</th>
			</tr>
			<c:forEach var="order" items="${list}">
			<tr>
				<td>${order.order_num}</td>
				<td><span class="order_user_emails">${order.user_email}</span></td>
				<td class="order_list_names"><a href="adminOrderListDetail.do?order_num=${order.order_num}"><span>${order.item_name}</span></a></td>
				<td><fmt:formatNumber value="${order.pay_total + order.pay_ship - order.pay_points}"/>원</td>
				<td>${order.order_date}</td>
				<td>
					<c:if test="${order.order_status == 1}">배송대기</c:if>
					<c:if test="${order.order_status == 2}">배송준비중</c:if>
					<c:if test="${order.order_status == 3}">배송중</c:if>
					<c:if test="${order.order_status == 4}"><b>배송완료</b></c:if>
					<c:if test="${order.order_status == 5}"><b>주문취소</b></c:if>
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
