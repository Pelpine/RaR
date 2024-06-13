<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ksh2.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="tltie">나의 리뷰</div>
<jsp:include page="/WEB-INF/views/review/reviewlist.jsp"/>
</body>
</html>