<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환불신청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/khc.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
<style>
	.order-detail-table{
	width:700px; 
	border-collapse: collapse;
	text-align  : left;
	}
	.order-detail-table .padding{
	padding-left : 15px;
	font-size : 14pt;
	}
	th{
	   border-bottom: 2px solid #333;
	   font-size : 16pt;
	}
	 .refund-policy {
        font-size: 12px; /* 글자 크기 */
        color: #666; /* 글자 색상 */
        margin-top: 10px; /* 위 여백 */
        text-align: left; /* 텍스트 우측 정렬 */
    }
    .refund-policy-container {
    text-align: left; /* 텍스트 우측 정렬 */
    margin-top: 10px; /* 위 여백 */
}

.refund-policy {
    font-size: 12px; /* 글자 크기 */
    color: #666; /* 글자 색상 */
}
.refund_price{
	border: 1px solid gray;
	width : 300px;
}
</style>
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h2 class="h2">환불 신청 정보</h2>    
        <!-- 테이블로 환불 상품 정보 표시 -->
        <div class="align-center">
	        <table class="order-detail-table">
	            <thead>
	                <tr>
	                    <th colspan="2">환불 상품</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td rowspan="${empty item ? 1 : 2}"><img src="${empty item ? '' : item.bk_img}" alt="상품 사진" width="100"></td>
	                    <td class="padding">
	                        <p>상품명: ${item.item_name}</p>
	                        <p>구매가격 : <span class="item_price"> ${item.item_price}</span>원</p>
	                        <p>
	                        <c:if test="${item.item_grade == 1}">
							<span class="item_grade1">상품등급 : 상</span>
							</c:if>
							<c:if test="${item.item_grade == 2}">
							<span class="item_grade2">상품등급 : 중</span>
							</c:if> 
							<c:if test="${item.item_grade == 3}">
							<span class="item_grade3">상품등급 : 하</span>
						</c:if>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
		</div>
	<hr size="1" width="100%" noshade="noshade">
	<p>
<div class="align-center">
		환불번호 : ${refund.refund_num}<br>
		신청일 : ${refund.request_date}<br>
		환불이유 : 
		<c:if test="${refund.reason == 1}">단순 변심</c:if>
		<c:if test="${refund.reason == 2}">상품 불량</c:if>
		<c:if test="${refund.reason == 3}">배송 문제</c:if>
		<c:if test="${refund.reason == 4}">기타</c:if>
		<br>
		<c:if test="${!empty refund.reason_other}"> 기타 이유 : ${refund.reason_other}</c:if>
		환불액 : ${refund.refund_price}원
		환불 상태 : 
		<c:if test="${refund.status == 1}">환불 신청</c:if>
		<c:if test="${refund.status == 2}">상품 불량</c:if>
		<c:if test="${refund.status == 3}">배송 문제</c:if>
		<c:if test="${refund.status == 4}">기타</c:if>
</div>	
	<hr size="1" width="100%" noshade="noshade">

	<div class="align-right">
			<input type="button" value="수정" onclick="location.href='modifyRefundForm.do?refund_num=${refund.refund_num}'">
			<input type="button" value="환불 취소" id="delete_btn">
			<script type="text/javascript">
				const delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick = function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('deleteRefund.do?refund_num=${refund.refund_num}');
					}
				};
			</script>		
		<input type="button" value="환불목록"
		       onclick="location.href='userRefundList.do'">
	</div>
</div>
</div>
	<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
