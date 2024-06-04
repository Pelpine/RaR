<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 상세 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<h2>룰렛 이벤트!</h2>
	<p>
		
	</p>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-center">
	
	<p>
		<iframe width="400" height="500" src="roulette.do"></iframe>
	</p>
	책을 한 권 구매할 때마다 주어지는 룰렛 티켓으로 포인트를 획득하세요!
</div>		
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-right">
		<input type="button" value="목록"
		       onclick="location.href='eventList.do'">
	</div>
</div>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>
