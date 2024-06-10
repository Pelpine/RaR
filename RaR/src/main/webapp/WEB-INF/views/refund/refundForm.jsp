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
        text-align: right; /* 텍스트 우측 정렬 */
    }
</style>
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h2 class="h2">환불 신청</h2>    

        <!-- 테이블로 환불 상품 정보 표시 -->
        <div class="align-center">
	        <table class="order-detail-table">
	            <thead>
	                <tr>
	                    <th colspan="2">구매한 상품</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td rowspan="${empty item ? 1 : 2}"><img src="${empty item ? '' : item.bk_img}" alt="상품 사진" width="100"></td>
	                    <td class="padding">
	                        <p>상품명: ${item.item_name}</p>
	                        <p>구매가격 : ${item.item_price}원</p>
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
		
        <!-- 환불 신청 폼 -->
        <div class="align-center">
        <form id="refund_form" action="refund.do" method="post">
    <ul>
    <!-- 환불 사유 선택 -->
    <li>
        <label for="reason">환불 사유</label>
        <select name="reason" id="reason_select" onchange="toggleTextarea()">
            <option value="1">단순 변심</option>
            <option value="2">상태 불량</option>
            <option value="3">배송 문제</option>
            <option value="4">기타</option>
        </select>
    </li>
    <!-- 기타 사유 입력 -->
    <li id="reason_other">
        <label for="reason_other_textarea">기타 사유</label>
        <textarea name="reason_other" id="reason_other_textarea" rows="4" cols="50" disabled></textarea>
    </li>
    <li id="bank">
        <label for="bank">은행</label>
        <input type="text" name="bank" maxlength="12">
    </li>
    <li id="account">
        <label for="account">계좌번호</label>
        <input type="text" name="account" maxlength="40">
    </li>
</ul> 
<span>환불 신청이 접수되면 상품을 업체주소로 배송해주세요.</span><br>
<span class="refund-policy">
	
</span>
<!-- 버튼 그룹 -->
<input type="submit" value="신청">
<input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/order/userOrderListDetail.do?order_num=${order_num}'">
</form>
</div>

<script>
function toggleTextarea() {
    var selectBox = document.getElementById("reason_select");
    var textarea = document.getElementById("reason_other_textarea");

    if (selectBox.value == "4") {
        textarea.disabled = false;
    } else {
        textarea.disabled = true;
        textarea.value = ""; // 다른 사유 선택 시 입력 내용 초기화
    }
}
</script>
<hr size="1" color="black">
</div>
</div>
	<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
