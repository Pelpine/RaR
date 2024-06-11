<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-main">
    <h4>리뷰</h4>
    <c:if test="${count2 == 0}">
        <div class="result-display1">표시할 리뷰가 없습니다.</div>
    </c:if>
    <c:if test="${count2 > 0}">
        <c:forEach var="re" items="${list2}">
            <div>별점 : ${re.re_rating}</div>
            <div>유저 이메일 : ${re.memberVO.user_email}</div>
            <div>리뷰 : ${re.re_comment}</div>
            <div>이미지 : <img src="${pageContext.request.contextPath}/upload/${re.re_img}" alt="리뷰 이미지"></div>
            <div>입력일 : ${re.reg_date}</div>
            <div>수정일 : ${re.modify_date}</div>
        </c:forEach>
    </c:if>
    <div class="align-center1">${page2}</div>
</div>