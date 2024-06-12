<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>(관리자)환불신청정보</title>
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
 .refund-container {
     max-width: 600px;
     margin: 50px auto;
     padding: 20px;
     border: 1px solid #ddd;
     border-radius: 10px;
     box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
     font-family: 'Arial', sans-serif;
     line-height: 1.6;
 }
 .refund-container h2 {
     text-align: center;
     color: #333;
 }
 .refund-item {
     margin: 10px 0;
 }
 .refund-label {
     font-weight: bold;
     color: #555;
 }
 .refund-value {
     margin-left: 10px;
 }
 .highlight {
     background-color: #f9f9f9;
     padding: 5px;
     border-radius: 5px;
 }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
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
<div class="refund-container">
    <h2>환불 상세 정보</h2>
    <div class="refund-item">
        <span class="refund-label">환불번호:</span>
        <span class="refund-value highlight">${refund.refund_num}</span>
    </div>
    <div class="refund-item">
        <span class="refund-label">상품번호:</span>
        <span class="refund-value highlight">${refund.item_num}</span>
    </div>
    <div class="refund-item">
        <span class="refund-label">신청일:</span>
        <span class="refund-value highlight">${refund.request_date}</span>
    </div>
    <div class="refund-item">
        <span class="refund-label">환불이유:</span>
        <span class="refund-value highlight">
            <c:if test="${refund.reason == 1}">단순 변심</c:if>
            <c:if test="${refund.reason == 2}">상품 불량</c:if>
            <c:if test="${refund.reason == 3}">배송 문제</c:if>
            <c:if test="${refund.reason == 4}">기타</c:if>
        </span>
    </div>
    <c:if test="${!empty refund.reason_other}">
    <div class="refund-item">
        <span class="refund-label">기타 이유:</span>
        <span class="refund-value highlight">${refund.reason_other}</span>
    </div>
    </c:if>
    <div class="refund-item">
        <span class="refund-label">환불액:</span>
        <span class="refund-value highlight">${refund.refund_price}원</span>
    </div>
    <div class="refund-item">
        <span class="refund-label">환불 받으실 계좌번호:</span>
        <span class="refund-value highlight">${refund.bank} / ${refund.account}</span>
    </div>
    <div>
    	<form action="adminModifyRefundStatus.do" method="post" id="status_modify">
		    <input type="hidden" name="refund_num" value="${refund.refund_num}">
		    <input type="hidden" name="unchangedStatus" value="${refund.status}">
		    <input type="hidden" name="refund_reason" value="${refund.reason}">
		    <input type="hidden" name="item_num" value="${refund.item_num}">
		    <input type="hidden" name="collect_point" value="${refund.collect_point}">
		    <input type="hidden" name="refund_user_num" value="${refund.user_num}">
		    <ul>
		        <li>
		            <label for="status">배송상태</label>
		                <input type="radio" name="status" id="status1" value="1"
		                    <c:if test="${refund.status == 1}">checked</c:if>>환불신청
		                <input type="radio" name="status" id="status2" value="2"
		                    <c:if test="${refund.status == 2}">checked</c:if>>환불검수중
		                <input type="radio" name="status" id="status3" value="3"
		                    <c:if test="${refund.status == 3}">checked</c:if>>환불 불가
		            <input type="radio" name="status" id="status4" value="4"
		                <c:if test="${refund.status == 4}">checked</c:if>>환불 완료
		        </li>
		        <li>
		            <label for="reason_other">환불불가이유</label>
		            <textarea name="unable_reason" id="unable_reason" rows="4" cols="50" disabled>${refund.unable_reason}</textarea>
		        </li>
		    </ul>
		    <div class="align-center">
		            <input type="submit" value="수정" <c:if test="${refund.status == 3 || refund.status== 4}">disabled</c:if>>
		    </div>
		</form>
    </div>
<script>
document.addEventListener('DOMContentLoaded', () => {
    const statusRadios = document.querySelectorAll('input[name="status"]');
    const unableReasonTextarea = document.getElementById('unable_reason');
    const initialStatus = document.querySelector('input[name="status"]:checked').value;

    function toggleTextarea() {
        const selectedStatus = document.querySelector('input[name="status"]:checked').value;

        if (selectedStatus === "3") {
            unableReasonTextarea.disabled = false;
        } else {
            unableReasonTextarea.disabled = true;
            unableReasonTextarea.value = ""; // 다른 사유 선택 시 입력 내용 초기화
        }
    }

    // 초기 status가 3이나 4이면 다른 라디오 버튼을 비활성화
    if (initialStatus === "3" || initialStatus == "4") {
        statusRadios.forEach(radio => {
            if (radio.value !== "3" || radio.value !== "4") {
                radio.disabled = true;
            }
        });
    }
 
    // 텍스트 박스 초기 설정
    toggleTextarea();

    // 라디오 버튼 변경 이벤트에 toggleTextarea 함수 바인딩
    statusRadios.forEach(radio => {
        radio.addEventListener('change', toggleTextarea);
    });
});
</script>    
</div>
	<hr size="1" width="100%" noshade="noshade">

	<div class="align-right">
		<input type="button" value="전체환불목록"
		       onclick="location.href='adminRefundList.do'">
	</div>
</div>
</div>
	<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
