<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>룰렛 돌리기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/roulette.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/RaR/js/roulette.js" defer></script>
</head>
<body>
<div style="border:1px solid black;">
<div>
  <canvas width="380" height="380"></canvas>  
  <button onclick="rotate()" <c:if test="${ticket <= 0}">disabled</c:if>>룰렛 돌리기</button>
  <span style="font-size:20px;">내가 보유한 티켓 : <span id="ticketCount">${ticket}</span></span>
</div>
</div>
</body>
</html>