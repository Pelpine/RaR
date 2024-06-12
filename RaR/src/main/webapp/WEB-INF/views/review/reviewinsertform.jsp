<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 작성</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/reviewinsert.css">
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<br>
	<br>
	<div class="page-main">
	<h2>상품 리뷰</h2>
		<c:forEach var="detail" items="${order}">
			<form action="insert.do" method="post" name="review_form">
				<input type="hidden" name="order_num" value="${detail.order_num}">
				<input type="hidden" name="detail_num" value="${detail.detail_num}">
				<div class="order-detail-container">
					<div class="item-image">
						<img src="${detail.bk_img}">
					</div>
					<div class="item-details">
						<table class="no_borderLR">
							<tr>
								<th colspan="3">상품정보</th>
								<th>가격</th>
								<th>등급</th>
							</tr>
							<tr class="row-divider">
								<!-- 상품정보 -->
								<td class="item_name" colspan="2"><a
									href="${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${detail.bk_num}">${detail.item_name}</a>
								</td>
								<!-- 가격 -->
								<td class="item_list_price" colspan="2">판매가 : <span
									class="item_item_price"><fmt:formatNumber
											value="${detail.item_price}" /></span>원
								</td>
								<!-- 책 등급 -->
								<td class="item-grade" colspan="1"><c:if
										test="${detail.item_grade == 1}">
										<span class="item_grade_1">상</span>
									</c:if> <c:if test="${detail.item_grade == 2}">
										<span class="item_grade_2">중</span>
									</c:if> <c:if test="${detail.item_grade == 3}">
										<span class="item_grade_3">하</span>
									</c:if></td>
							</tr>

						</table>
					</div>
				</div>
				<!-- 구분선 추가 -->
				<div class="review-container">
					<div>리뷰 남기기</div>
					<textarea rows="5" cols="30" id="re_comment" name="re_comment"></textarea>
					<label for="re_img">상품사진</label> <input type="file" name="re_img"
						class="input-check" id="re_img"
						accept="image/gif,image/png,image/jpeg">
				</div>
				<!-- 별점 주는 기능을 이동한 부분 -->
				<div class="rating-container">
					<div class="rating">
						<input type="radio" name="rating" id="rate-5" value="5"><label
							for="rate-5"></label> <input type="radio" name="rating"
							id="rate-4" value="4"><label for="rate-4"></label> <input
							type="radio" name="rating" id="rate-3" value="3"><label
							for="rate-3"></label> <input type="radio" name="rating"
							id="rate-2" value="2"><label for="rate-2"></label> <input
							type="radio" name="rating" id="rate-1" value="1" checked><label
							for="rate-1"></label>
					</div>
					<div class="selected-rating">1/5</div>
				</div>
				<input type="submit" value="댓글등록">
			</form>
		</c:forEach>
	</div>
	<script type="text/javascript">
$(document).ready(function() {
    const ratingContainer = $('.rating');
    const ratingInputs = $('input[name="rating"]');
    const selectedRatingDisplay = $('.selected-rating');

    let fixedRatingValue = $('input[name="rating"]:checked').val();

    ratingContainer.on('mouseover', 'label', function() {
        const ratingValue = $(this).prev('input').val();
        updateStars(ratingValue);
    });

    ratingContainer.on('mouseout', function() {
        updateStars(fixedRatingValue);
    });

    ratingInputs.on('change', function() {
        const ratingValue = $(this).val();
        fixedRatingValue = ratingValue;
        updateDisplayRating(ratingValue);
    });

    ratingContainer.on('click', 'label', function() {
        const ratingValue = $(this).prev('input').val();
        fixedRatingValue = ratingValue;
        updateDisplayRating(ratingValue);
    });

    function updateStars(ratingValue) {
        ratingInputs.each(function() {
            const label = $(this).next('label');
            if ($(this).val() <= ratingValue) {
                label.addClass('full');
            } else {
                label.removeClass('full');
            }
        });
    }

    function updateDisplayRating(ratingValue) {
        selectedRatingDisplay.text(ratingValue + '/5');
    }

    updateDisplayRating(fixedRatingValue);
    updateStars(fixedRatingValue);
});
</script>
</body>
</html>
