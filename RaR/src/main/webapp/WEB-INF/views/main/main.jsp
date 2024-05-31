<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="page-main"> 
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h4>메인 페이지</h4>
    </div>
    <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <c:forEach var="event" items="${list}" varStatus="loop">
            <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="${loop.index}" class="${loop.index == 0 ? 'active' : ''}"></button>
        </c:forEach>
    </div>
    <div class="carousel-inner">
        <c:forEach var="event" items="${list}" varStatus="loop">
        	<c:if test="${!empty event.banner}">
            <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                <a href="../event/eventDetail.do?event_num=${event.event_num}"><img src="${pageContext.request.contextPath}/upload/${event.banner}" class="d-block w-100"></a>
                <div class="carousel-caption">
                    <!-- 여기에 캐러셀 캡션 추가 -->
                </div>
            </div>
            </c:if>
        </c:forEach>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon"></span>
        <span class="visually-hidden">Previous</span>                             
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon"></span>
        <span class="visually-hidden">Next</span>                             
    </button>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>