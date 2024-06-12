<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="${pageContext.request.contextPath}/js/reviewdelete.js"></script>
<div class="page-main">
    <h4>리뷰</h4>
    <c:if test="${count2 == 0}">
        <div class="result-display1">표시할 리뷰가 없습니다.</div>
    </c:if>
    <c:if test="${count2 > 0}">
        <c:forEach var="re" items="${list2}">
            <div class="review">
                <div>별점 : ${re.re_rating}</div>
                <div>유저 이메일 : ${re.memberVO.user_email}</div>
                <div>이미지 : <img src="${pageContext.request.contextPath}/upload/${re.re_img}" alt="리뷰 이미지"></div>
                <div>입력일 : ${re.reg_date}</div>
                <div>수정일 : ${re.modify_date}</div>
                <div>리뷰 : <p>${re.re_comment}</p></div>
                <div class="sub-item">
                    <input type="button" value="수정" class="update" name="update" data-renum="${re.re_num}">
                    <input type="button" value="삭제" class="delete" name="delete" data-renum="${re.re_num}">
                </div>
            </div>
        </c:forEach>
    </c:if>
    <div class="align-center1">${page2}</div>
</div>
