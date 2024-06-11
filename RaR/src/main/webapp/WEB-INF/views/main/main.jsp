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
	            <div class="carousel-item active">
	                <img src="../images/독후감 이벤트.jpg">
	            </div>
	            <div class="carousel-item">
	                <img src="../images/출석체크 이벤트.png">
	            </div>
	            <div class="carousel-item">
	                <img src="../images/친구초대 이벤트.png">
	            </div>
	        </div>
	        <a class="carousel-control prev" onclick="prevSlide()"><img src="../images/left_btn.png"></a>
	        <a class="carousel-control next" onclick="nextSlide()"><img src="../images/right_btn.png"></a>
	    </div>
		<!-- 캐러셀 끝 -->
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