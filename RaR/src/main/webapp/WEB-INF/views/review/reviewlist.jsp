<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- CSS 파일 포함 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/review.css">
<!-- jQuery 파일 포함 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- JavaScript 파일 포함 -->
<script src="${pageContext.request.contextPath}/js/reviewdelete.js"></script>

<div class="page-main">
    <div class="tltie">리뷰</div>
    <c:if test="${count2 == 0}">
        <div class="result-display1">표시할 리뷰가 없습니다.</div>
    </c:if>
    <c:if test="${count2 > 0}">
        <div class="review-container">
            <c:forEach var="re" items="${list2}">
                <div class="review-item">
                    <div class="rating-stars">
                        <!-- 별점 표시 -->
                        <c:forEach var="i" begin="1" end="${re.re_rating}">
                            ★
                        </c:forEach>
                        <c:forEach var="i" begin="1" end="${5 - re.re_rating}">
                            <span class="empty">☆</span>
                        </c:forEach>
                    </div>
                    <div class="review-image">
                        <c:if test="${not empty re.re_img}">
                            <img src="${pageContext.request.contextPath}/upload/${re.re_img}" alt="리뷰 이미지" width="150" height="150">
                        </c:if>
                    </div>
                    <div class="review-comment">
                        <p>${re.re_comment}</p>
                    </div>
                    <div class="review-details">
                        <div>${re.memberVO.user_email}</div>
                        <div class="modify-date">
                            <c:choose>
                                <c:when test="${not empty re.modify_date}">
                                    수정됨 : ${re.modify_date}
                                </c:when>
                                <c:otherwise>
                                    ${re.reg_date}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="review-buttons sub-item">
                            <input type="button" value="리뷰수정" class="update" name="update" data-renum="${re.re_num}">
                            <input type="button" value="삭제" class="delete" name="delete" data-renum="${re.re_num}">
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <div class="align-center1">${page2}</div>
</div>
