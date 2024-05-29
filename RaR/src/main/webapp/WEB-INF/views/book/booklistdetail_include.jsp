<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="page-main">

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
				<!-- 장바구니 총 구매비용 계산, 주문정보에 전송할 데이터 포함 -->
				<tr id="cart_total">
					<!-- 주문상품 수량 -->
					<td><input type="hidden" name="totalCount" id="totalCount" value="0">0</td>
					<!-- 총 주문상품 금액 -->
					<td><input type="hidden" name="totalPrice" id="totalPrice" value="0">0</td>
					<!-- 배송비 -->
					<td><input type="hidden" name="ship" id="ship" value="0">0</td>
					<!-- 총 결제금액 -->
					<td><input type="hidden" name="totalPayment" id="totalPayment" value="0">0</td>
					<!-- 예상 적립 포인트 -->
					<td><input type="hidden" name="totalPoints" id="totalPoints" value="0">0</td>	
				</tr>
			</table>
			<span>총 주문상품 금액이 30000원 미만인 경우 배송비 4000원이 추가됩니다.<br> </span>
			<input type="submit" value="주문하기">
		</form>
	</div>
</div>