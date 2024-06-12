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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main"> 
    <div class="content-main">
    	<!-- 캐러셀 시작 -->
    	<div class="carousel">
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
	        <a class="carousel-control prev" onclick="prevSlide()"><img src="../images/left_btn.png"></a>
	        <a class="carousel-control next" onclick="nextSlide()"><img src="../images/right_btn.png"></a>
	    </div>
		<!-- 캐러셀 끝 -->
       <div class="image-space">
         <div class="Topic"><h2 class="Topic-h4">&nbsp 최근 입고된 중고 서적</h2></div>
         <c:forEach var="item" items="${itemList}">
         <div class="horizontal-area">
             <img src="${item.bookVO.bk_img}" 
             onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}&re_num=2'">
             <h4 class="name" onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}&re_num=2'">${item.bookVO.bk_name}</h4>
         </div>
         </c:forEach>
      </div>
      <div class="image-space">
         <div class="Topic"><h2 class="Topic">&nbsp 인기 급상승 도서</h2></div>
         <c:forEach var="item" items="${topList}">
         <div class="horizontal-area">
             <img src="${item.bookVO.bk_img}" 
             onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}&re_num=2'">
               <h4 class="name" onclick="location.href='${pageContext.request.contextPath}/book/booksdetail.do?bk_num=${item.bk_num}&re_num=2'">${item.orderDetailVO.item_name}</h4>
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