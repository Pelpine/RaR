<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yhl.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/item_detail.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div>
		<img alt="" src="${book.bk_img}">
	</div>
	<div>
		제목 : ${book.bk_name}
	</div>
	<div>
		저자 : ${book.bk_writer}
	</div>
	<div>
		출판사 : ${book.bk_publisher}
	</div>
	<div>
		정가 : ${book.bk_price}
	</div>
	<div>
		설명 : ${book.bk_description}
	</div>
	<div>
		최저가 : ${book.itemVO.item_price}
	</div>
</div>


<jsp:include page="/WEB-INF/views/book/booklistdetail_include.jsp"/>
</body>
</html>