<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
</head>
<body>   
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>주문 상세</h2>
		<table>
			<tr>
				<th>상품정보</th>
				<th>가격</th>
				<th>등급</th>
				<c:if test="${order.order_status == 4 && currentDate < deadline}"><th>환불</th></c:if>
			</tr>
			<c:forEach var="detail" items="${detailList}">
			<tr>
				<td>
					<img src="${detail.bk_img}" width="60">
					<%-- 상품이미지가 없을 경우, 기본 이미지 처리 --%>
					<c:if test="${detail.item_img == null}">
						<img src="../images/book_default.png" width="60">
					</c:if> <%-- 상품이미지가 있을 경우 --%> 
					<c:if test="${detail.item_img != null}">
						<a href="javascript:void(0);" onclick="showPopup('${pageContext.request.contextPath}/upload/${detail.item_img}')">
						<img src="${pageContext.request.contextPath}/upload/${detail.item_img}" width="60"></a>
					</c:if>
					<a href="${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${detail.bk_num}">${detail.item_name}</a>
				</td>
				<%-- 가격 --%>
				<td class="align-center">
				<span class="item_bk_price">정가 : <fmt:formatNumber value="${detail.bk_price}"/>원</span><br>
				판매가 : <span class="item_item_price"><fmt:formatNumber value="${detail.item_price}"/></span>원
				</td>
				<%-- 책 등급 --%>
				<td class="align-center">
					<c:if test="${detail.item_grade == 1}">
						<span class="item_grade1">상</span>
					</c:if>
					<c:if test="${detail.item_grade == 2}">
						<span class="item_grade2">중</span>
					</c:if> 
					<c:if test="${detail.item_grade == 3}">
						<span class="item_grade3">하</span>
					</c:if>
				</td>
				<c:if test="${order.order_status == 4 && currentDate < deadline}"><td><input type="button" value="환불 신청" 
				onclick="location.href='${pageContext.request.contextPath}/refund/refundForm.do?item_num=${detail.item_num}&order_num=${order.order_num}'"></td></c:if>
			</tr>
			</c:forEach>
		</table>
		<p>
		<table>	
			<tr>
				<th class="align-center"><b>결제내역</b><br>(총 구매금액 + 배송비 - 사용포인트)</th>
				<th class="align-center"><b>총 결제금액</b></th>
				<th class="align-center">적립포인트</th>
			</tr>
			<tr>
				<!-- 결제내역 -->
				<td class="align-center">
				<fmt:formatNumber value="${order.pay_total}" type="number"/>  +  
				<fmt:formatNumber value="${order.pay_ship}" type="number"/>  -  
				<fmt:formatNumber value="${order.pay_points}" type="number"/>
				</td>
				<!-- 총 결제금액 -->
				<td class="align-center totalPayment">
				<span id="totalPayment"><fmt:formatNumber value="${order.pay_total + order.pay_ship - order.pay_points}" type="number"/></span>원
				</td>
				<!-- 적립포인트 -->
				<td class="align-center"><fmt:formatNumber value="${order.order_points}" type="number"/>p</td>
			</tr>
		</table>
		<ul id="delivery_info">
			<li>
				<span>주문 날짜 : </span> ${order.order_date} <span>/ 환불 기한 : </span>${deadline}		
			</li>
			<li>
				<span>받는 사람 : </span> ${order.receive_name}
			</li>
			<li>
				<span>우편 번호 : </span> ${order.receive_post}
			</li>
			<li>
				<span>주소 : </span> ${order.receive_address1} ${order.receive_address2}
			</li>
			<li>
				<span>전화번호 : </span> ${order.receive_phone}
			</li>
			<li>
				<span>남기실 말씀 : </span> ${order.notice}
			</li>
			<li>
				<span>결제수단 : </span>
				<c:if test="${order.pay_payment == 1}">계좌입금</c:if>
				<c:if test="${order.pay_payment == 2}">카드결제</c:if>
			</li>
			<li>
				<span>배송상태 : </span>
				<c:if test="${order.order_status == 1}">배송대기</c:if>
				<c:if test="${order.order_status == 2}">배송준비중</c:if>
				<c:if test="${order.order_status == 3}">배송중</c:if>
				<c:if test="${order.order_status == 4}">배송완료</c:if>
				<c:if test="${order.order_status == 5}">주문취소</c:if>				
			</li>
			<li class="align-center">
				<c:if test="${order.order_status == 1}">
				<input type="button" value="배송지정보수정" onclick="location.href='orderModifyForm.do?order_num=${order.order_num}'">
				<input type="button" value="주문 취소" id="order_cancel">
				<script>
					const order_cancel = document.getElementById('order_cancel');
					order_cancel.onclick=function(){
						let choice = confirm('주문을 취소하시겠습니까?');
						if(choice){
							location.replace('orderCancel.do?order_num=${order.order_num}');//history 지우기 위해 href 말고 replace 사용
						}
					};
				</script>
				</c:if>
				<input type="button" value="주문목록" onclick="location.href='userOrderList.do'">
				<input type="button" value="MY페이지" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
			</li>
		</ul>
	</div>
</div>
</body>
</html>