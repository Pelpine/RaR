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
	
}
</style>
<script type="text/javascript">
window.onload = function() {
    const myForm = document.getElementById('modify_form');
    // 이벤트 연결
    myForm.onsubmit = function() {
        const items = document.querySelectorAll('.input-check');
        for (let i = 0; i < items.length; i++) {
            if (items[i].value.trim() == '') {
                const label = document.querySelector('label[for="' + items[i].id + '"]');
                alert(label.innerText + ' 항목은 필수 입력');
                items[i].focus();
                return false;
            }
        }
    }
}
</script>
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h2 class="h2">환불 내용 수정</h2>    

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
		
        <!-- 환불 신청 폼 -->
        <div class="align-center">
        <form id="modify_form" action="modifyRefund.do" method="post">
        	<input type="hidden" value="${refund.refund_num}" name="refund_num">
        	<input type="hidden" value="${refund.status}" name="status">
	        <input type="hidden" value="${refund.order_num}" name="order_num">
	        <input type="hidden" value="${refund.item_num}" name="item_num" id="item_num">
    		<ul>
			    <!-- 환불 사유 선택 -->
			    <li>
			        <label for="reason">환불 사유</label>
			        <select name="reason" id="reason" onchange="toggleTextarea()" class="input-check">
			            <option value="1" ${refund.reason == 1 ? 'selected' : ''}>단순 변심</option>
			            <option value="2" ${refund.reason == 2 ? 'selected' : ''}>상태 불량</option>
			            <option value="3" ${refund.reason == 3 ? 'selected' : ''}>배송 문제</option>
			            <option value="4" ${refund.reason == 4 ? 'selected' : ''}>기타</option>
			        </select>
			    </li>
			    <!-- 기타 사유 입력 -->
			    <li>
			        <label for="reason_other">기타 사유</label>
			        <textarea name="reason_other" id="reason_other" rows="4" cols="50" disabled>${refund.reason_other}</textarea>
			    </li>
			    <li id="bank">
			        <label for="bank">은행</label>
			        <input type="text" name="bank" maxlength="12" class="input-check" id="bank" value="${refund.bank}">
			    </li>
			    <li id="account">
			        <label for="account">계좌번호</label>
			        <input type="text" name="account" maxlength="40" class="input-check" id="account" value="${refund.account}">
			    </li>
			</ul> 
			<!-- 버튼 그룹 -->
			<input type="submit" value="수정">
			<input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/order/userRefundDetail.do?item_num=${refund.item_num}'">
			<div class="refund-policy-container">
</div>
</form>
</div>
<script>
function toggleTextarea() {
    var selectBox = document.getElementById("reason");
    var textarea = document.getElementById("reason_other");

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
