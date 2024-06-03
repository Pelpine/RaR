<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
	<div class="content-main">
		<c:if test="${count == 0}">
		<div class="result-display">
			해당 책에 대한 상품이 없습니다.
		</div>
		</c:if>
		<!-- 상품 목록 -->
		<c:if test="${count > 0}">
		<table>
				<tr>
					<th>상품정보</th>
					<th>등급</th>
					<th>판매가</th>
					<th>장바구니 담기</th>
				</tr>
				<c:forEach var="item[i]" items="${list}">
				<tr>
					<td>
				    <img src="${item.bookVO.bk_img}" width="60">
				    <img src="${pageContext.request.contextPath}/upload/${item.item_img}" width="60">
				    </td>
					<td>${item.item_grade}</td>
					<td>${item.item_price}</td>
					<td>
					<form id="insertCart" style="width:0;margin:10px 0;border:0;padding:0;">
						<input type="hidden" name="item_num" value="${item.item_num}">
						<input type="hidden" name="bk_num" value="${item.bk_num}">
						<input type="submit" value="장바구니 담기">
					</form>
					</td>						
				</tr>
				</c:forEach>
		</table>
		<!-- 페이지 -->
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>