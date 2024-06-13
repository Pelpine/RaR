<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-main">
	<div class="content-main" id="to">
		<c:if test="${count == 0}">
			<div class="result-display">해당 책의 판매 가능한 상품이 없습니다.</div>
		</c:if>
		<!-- 상품 목록 -->
		<c:if test="${count > 0}">
			<table id="booklist_detail" class="no_borderLR">
				<tr>
					<th colspan="3">상품정보</th>
					<th>등급</th>
					<th>판매가</th>
					<th>장바구니 담기</th>
				</tr>
				<c:forEach var="item" items="${list}">
					<c:if test="${item.item_status == 1}">
					<tr>
						<td class="item_img">
							<img src="${item.bookVO.bk_img}">
						</td>
						<td class="item_img">
							<%-- 상품이미지가 없을 경우, 기본 이미지 처리 --%>
							<c:if test="${item.item_img == null}">
								<img src="../images/book_default.png">
							</c:if> <%-- 상품이미지가 있을 경우 --%> 
							<c:if test="${item.item_img != null}">
								<a href="javascript:void(0);" onclick="showPopup('${pageContext.request.contextPath}/upload/${item.item_img}')">
								중고책 상태<br> 보기</a>
							</c:if>
						</td>
						<td class="item_name">
							${item.bookVO.bk_name}
						</td>
						<%-- 책 등급 --%>
						<td>
							<c:if test="${item.item_grade == 1}">
								<span class="item_grade_1">상</span>
							</c:if>
							<c:if test="${item.item_grade == 2}">
								<span class="item_grade_2">중</span>
							</c:if> 
							<c:if test="${item.item_grade == 3}">
								<span class="item_grade_3">하</span>
							</c:if>
						</td>
						<%-- 가격 --%>
						<td class="item_list_price">
						<span class="item_bk_price">정가 : <fmt:formatNumber value="${item.bookVO.bk_price}"/>원</span><br>
						판매가 : <span class="item_item_price"><fmt:formatNumber value="${item.item_price}"/></span>원
						</td>
						<%-- 장바구니 담기 --%>
						<td>
							<form class="insertCart" method="post">
								<input type="hidden" name="item_num" value="${item.item_num}">
								<input type="hidden" name="bk_num" value="${item.bk_num}">
								<input type="submit" value="장바구니 담기">
							</form>
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
			<!-- 페이지 -->
			<div class="pageNum">${page}</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
function showPopup(imgSrc) {
    var popupWindow = window.open("", "popupWindow", "width=400,height=400,scrollbars=yes");

    popupWindow.document.write("<html><head><title>이미지 팝업</title></head><body>");
    popupWindow.document.write("<style>");
    popupWindow.document.write("body { display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
    popupWindow.document.write("img { max-width: 100%; height: 100%; }");
    popupWindow.document.write("</style>");
    popupWindow.document.write("<img src='" + imgSrc + "'onclick='window.close();'><br>");
    popupWindow.document.write("</body></html>");
}
</script>
