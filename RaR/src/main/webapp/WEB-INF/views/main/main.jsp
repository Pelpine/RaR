<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>메인</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main"> 
    <div class="content-main">
          <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-indicators">
              <c:forEach var="event" items="${list}" varStatus="loop">
              	<c:if test="${!empty event.banner}">
                  <button type="button" data-bs-target="#carouselExample" data-bs-slide-to="${loop.index}" class="${loop.index == 0 ? 'active' : ''}"></button>
            	</c:if>
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
       <div class="image-space">
         <div class="Topic"><h4 class="Topic-h4">최근 입고된 중고 서적</h4></div>
         <c:forEach var="item" items="${itemList}">
         <div class="horizontal-area">
             <img src="${item.bookVO.bk_img}" 
             onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}'">
             <h6 class="name" onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}'">${item.bookVO.bk_name}</h6>
         </div>
         </c:forEach>
      </div>
      <div class="image-space">
         <div class="Topic"><h4 class="Topic">인기 급상승 도서</h4></div>
         <c:forEach var="item" items="${topList}">
         <div class="horizontal-area">
             <img src="${item.bookVO.bk_img}" 
             onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}'">
               <h6 class="name" onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}'">${item.orderDetailVO.item_name}</h6>
         </div>
         </c:forEach>
      </div>
   </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>