<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<div class="content-main">
		<table>
			<tr>
				<th><input type="checkbox"></th>
				<th>상품정보</th>
				<th>가격</th>
				<th>상태</th>
				<th>삭제</th>
			</tr>
			<tr>
				<td><input type="checkbox"></td>
				<td><img src="../images/face.png" width="30" height="40">상품명</td>
				<td>10,000</td>
				<td>상</td>
				<td><input type="button" value="삭제"></td>
			</tr>
			<tr>
				<td><input type="checkbox"></td>
				<td><img src="../images/face.png" width="30" height="40">상품명</td>
				<td>10,000</td>
				<td>상</td>
				<td><input type="button" value="삭제"></td>
			</tr>
		</table>
		<p>
		<input type="button" value="선택상품 삭제">
		<p>
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
	</div>
</div>
</body>
</html>
