<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 상세(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
</head>
<body>   
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>주문 상세(관리자 전용)</h2>
		<table id="order_detail" class="no_borderLR">
			<tr>
				<th colspan="3">상품정보</th>
				<th>가격</th>
				<th>등급</th>
			</tr>
			<c:forEach var="detail" items="${detailList}">
			<tr>
				<%-- 상품정보 --%>
				<td class="item_img">
					<img src="${detail.bk_img}">	
				</td>
				<td class="item_img">
					<%-- 상품이미지가 없을 경우, 기본 이미지 처리 --%>
					<c:if test="${detail.item_img == null}">
						<img src="../images/book_default.png" width="60">
					</c:if> <%-- 상품이미지가 있을 경우 --%> 
					<c:if test="${detail.item_img != null}">
						<a href="javascript:void(0);" onclick="showPopup('${pageContext.request.contextPath}/upload/${detail.item_img}')">
						<img src="${pageContext.request.contextPath}/upload/${detail.item_img}"></a>
					</c:if>
				</td>
				<td class="item_name">
					<a href="${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${detail.bk_num}&re_num=2">${detail.item_name}</a>
				</td>
				<%-- 가격 --%>
				<td class="item_list_price">
				<span class="item_bk_price">정가 : <fmt:formatNumber value="${detail.bk_price}"/>원</span><br>
				판매가 : <span class="item_item_price"><fmt:formatNumber value="${detail.item_price}"/></span>원
				</td>
				<%-- 책 등급 --%>
				<td>
					<c:if test="${detail.item_grade == 1}">
						<span class="item_grade_1">상</span>
					</c:if>
					<c:if test="${detail.item_grade == 2}">
						<span class="item_grade_2">중</span>
					</c:if> 
					<c:if test="${detail.item_grade == 3}">
						<span class="item_grade_3">하</span>
					</c:if>
				</td>
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
			<tr class="pay_content">
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
		<div class="order_title">
			<h3>주문 정보</h3>
			<hr size="3" width="100%" color="#000">
		</div>
		<ul class="delivery_info">
			<li>
				<span class="info_title">주문 날짜</span> ${order.order_date}
			</li>
			<li>
				<span class="info_title">환불 기한</span> ${deadline}
			</li>
			<li>
				<span class="info_title">받는 사람</span> ${order.receive_name}
			</li>
			<li>
				<span class="info_title">우편 번호</span> ${order.receive_post}
			</li>
			<li>
				<span class="info_title">주소</span> <span class="info_content">${order.receive_address1} ${order.receive_address2}</span>
			</li>
			<li>
				<span class="info_title">전화번호</span> ${order.receive_phone}
			</li>
			<li>
				<span class="info_title">남기실 말씀</span> <span class="info_content">${order.notice}</span>
			</li>
			<li>
				<span class="info_title">결제수단</span>
				<c:if test="${order.pay_payment == 1}">계좌입금</c:if>
				<c:if test="${order.pay_payment == 2}">카드결제</c:if>
			</li>
			<li>
				<span class="info_title">배송상태</span>
				<c:if test="${order.order_status == 1}">배송대기</c:if>
				<c:if test="${order.order_status == 2}">배송준비중</c:if>
				<c:if test="${order.order_status == 3}">배송중</c:if>
				<c:if test="${order.order_status == 4}"><b>배송완료</b></c:if>
				<c:if test="${order.order_status == 5}"><b>주문취소</b></c:if>				
			</li>
			<li class="order_detail_button">
				<c:if test="${order.order_status != 5}">
				<input type="button" value="배송상태수정" onclick="location.href='modifyStatusForm.do?order_num=${order.order_num}'">
				</c:if>
				<c:if test="${order.order_status == 5}">
				<input type="button" value="배송상태수정" disabled>
				</c:if>
				<input type="button" value="주문목록" onclick="location.href='adminOrderList.do'">
			</li>
		</ul>
	</div>
</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>