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
	<h2>${event.name}</h2>
	<p>
		이벤트 번호 : ${event.event_num}<br>
		등록일 : ${event.reg_date}
		시작일 : ${event.start_date}
		종료일 : ${event.end_date}
		조회수 : ${event.hit}
	</p>
	<hr size="1" width="100%" noshade="noshade">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/upload/${event.filename}"
		                    style="max-width: 500px">
	</div>
	<p>
		${event.content}
	</p>
		
	<hr size="1" width="100%" noshade="noshade">
	
	<div class="align-right">
		<c:if test="${user_num == event.user_num}">
			<input type="button" value="수정" onclick="location.href='updateEventForm.do?event_num=${event.event_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				const delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick = function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('deleteEvent.do?event_num=${event.event_num}');
					}
				};
			</script>
		</c:if>
		
		<input type="button" value="목록"
		       onclick="location.href='eventList.do'">
	</div>
</div>
</body>
</html>

