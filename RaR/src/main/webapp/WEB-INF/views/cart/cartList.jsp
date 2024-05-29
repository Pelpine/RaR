<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<form id="cart_order" action="${pageContext.request.contextPath}/order/orderForm.do" method="post">
			<!-- 장바구니 상품 목록, 동적으로 생성 -->
			<table id="output"></table>
			<p>
			<!-- 전체 상품 삭제 버튼 -->
			<input type="button" value="선택상품 삭제" id="selectAll-btn">
			<p>
			<!-- 장바구니 연산 결과 -->
			<table>
				<tr>
					<th>주문상품 수량</th>
					<th>총 주문상품 금액</th>
					<th>배송비</th>
					<th>총 결제금액</th>
					<th>예상 적립 포인트</th>
				</tr>
				<tr>
					<td>2</td>
					<td>20,000</td>
					<td>4,000</td>
					<td>24,000</td>
					<td>240</td>
				</tr>
			</table>
			<input type="submit" value="주문하기">
		</form>
	</div>
</div>
</body>
</html>