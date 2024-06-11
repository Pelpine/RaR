<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 작성</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reviewinsert.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<div class="page-main">
    <c:forEach var="detail" items="${order}">
        <div>
            <table id="order_detail" class="no_borderLR">
            <tr>
                <th colspan="3">상품정보</th>
                <th>가격</th>
                <th>등급</th>
            </tr>
            
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
                        <img src="${pageContext.request.contextPath}/upload/${detail.item_img}" width="60"></a>
                    </c:if>
                </td>
                <td class="item_name">
                    <a href="${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${detail.bk_num}">${detail.item_name}</a>
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
        </table>
        </div>
        <div>
            <div>코멘트</div>
            <textarea rows="5" cols="30" id="comment" name="comment"></textarea>
            <label for="photo">상품사진</label>
            <input type="file" name="photo" class="input-check" id="photo" accept="image/gif,image/png,image/jpeg">
                <div class="rating-container">
    <div class="rating">
        <input type="radio" name="rating" id="rate-5" value="5"><label for="rate-5"></label>
        <input type="radio" name="rating" id="rate-4" value="4"><label for="rate-4"></label>
        <input type="radio" name="rating" id="rate-3" value="3"><label for="rate-3"></label>
        <input type="radio" name="rating" id="rate-2" value="2"><label for="rate-2"></label>
        <input type="radio" name="rating" id="rate-1" value="1" checked><label for="rate-1"></label>
    </div>
    <div class="selected-rating">1/5</div>
</div>

        </div>
    </c:forEach>
</div>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    const ratingContainer = document.querySelector('.rating');
    const ratingInputs = document.querySelectorAll('input[name="rating"]');
    const selectedRatingDisplay = document.querySelector('.selected-rating');
    let isRatingFixed = false;

    ratingContainer.addEventListener('mouseover', function(event) {
        if (!isRatingFixed) {
            const targetLabel = event.target.closest('label');
            if (targetLabel) {
                const ratingValue = targetLabel.previousElementSibling.value;
                updateStars(ratingValue);
            }
        }
    });

    ratingContainer.addEventListener('mouseout', function(event) {
        if (!isRatingFixed) {
            const checkedInput = document.querySelector('input[name="rating"]:checked');
            if (checkedInput) {
                updateStars(checkedInput.value);
            } else {
                updateStars(0);
            }
        }
    });

    ratingInputs.forEach(input => {
        input.addEventListener('change', function() {
            const ratingValue = this.value;
            selectedRatingDisplay.textContent = `${ratingValue}/5`;
            isRatingFixed = true;
        });
    });

    ratingContainer.addEventListener('click', function(event) {
        const targetLabel = event.target.closest('label');
        if (targetLabel) {
            const ratingValue = targetLabel.previousElementSibling.value;
            selectedRatingDisplay.textContent = `${ratingValue}/5`;
            isRatingFixed = true;
        }
    });

    function updateStars(ratingValue) {
        ratingInputs.forEach(input => {
            const label = input.nextElementSibling;
            if (input.value <= ratingValue) {
                label.classList.add('full');
            } else {
                label.classList.remove('full');
            }
        });
    }

    // 초기 로딩 시 선택된 별점 표시
    const initialRating = document.querySelector('input[name="rating"]:checked').value;
    selectedRatingDisplay.textContent = `${initialRating}/5`;
    updateStars(initialRating);
});
</script>
</body>
</html>
