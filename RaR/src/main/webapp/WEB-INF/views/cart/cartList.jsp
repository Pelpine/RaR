<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<form id="cart_order" action="${pageContext.request.contextPath}/order/orderForm.do" method="post">
			<!-- 선택상품 hidden -->
			<div id="selectedItems"></div>
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
				<!-- 장바구니 총 구매비용 계산, 주문정보에 전송할 데이터 포함 -->
				<tr id="cart_total">
					<!-- 주문상품 수량 -->
					<td><input type="hidden" name="totalCount" id="totalCount" value="0">0</td>
					<!-- 총 주문상품 금액 -->
					<td><input type="hidden" name="totalPrice" id="totalPrice" value="0">0</td>
					<!-- 배송비 -->
					<td><input type="hidden" name="ship" id="ship" value="0">0</td>
					<!-- 총 결제금액 -->
					<td class="totalPayment"><input type="hidden" name="totalPayment" id="totalPayment" value="0">0</td>
					<!-- 예상 적립 포인트 -->
					<td><input type="hidden" name="totalPoints" id="totalPoints" value="0">0</td>	
				</tr>
			</table>
			<span>총 주문상품 금액이 30000원 미만인 경우 배송비 4000원이 추가됩니다.<br> </span>
			<input type="submit" value="주문하기">
		</form>
	</div>
</div>
</body>
</html>