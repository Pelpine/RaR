<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></button>
            <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="1"></button>
            <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="2"></button>
            <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="3"></button>                                        
        </div>
        <div class="carousel-inner">
            <!-- 첫 번째 item -->
            <div class="carousel-item active">
                <a href="../event/eventDetail.do?event_num=35"><img src="../images/출석체크 이벤트.png" class="d-block w-100"></a>
                <div class="carousel-caption">
                    <p>출석체크 페이지로 이동</p>
                </div>
            </div>
            <!-- 두 번째 item -->
            <div class="carousel-item">
               <a href="../event/eventDetail.do?event_num=42"><img src="../images/친구초대 이벤트.png" class="d-block w-100"></a>
                <div class="carousel-caption">
                </div>
            </div>
            <!-- 세 번째 item -->
            <div class="carousel-item">
                <a href="../event/eventDetail.do?event_num=38"><img src="../images/독후감 이벤트.jpg" class="d-block w-100"></a>
                <div class="carousel-caption">
                    <h5>Third slide label</h5>
                    <p>Lighthouse 이미지~~</p>
                </div>
            </div>
            <!-- 네 번째 item -->
            <div class="carousel-item">
                <img src="../images/Tulips02.jpg" class="d-block w-100">
                <div class="carousel-caption">
                    <h5>Forth slide label</h5>
                    <p>Tulip 이미지~~</p>
                </div>
            </div>
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