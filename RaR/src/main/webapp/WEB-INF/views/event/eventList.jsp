<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/khc.css" type="text/css">
<script type="text/javascript">
    window.onload = function(){
        const myForm = document.getElementById('search_form');
        myForm.onsubmit = function(){
            const underway_btn = document.getElementById('underway_btn');
            if(underway_btn.checked){
                underway_btn.value = "1";
            }else{
                underway_btn.value = "0";
            }
            console.log(underway.value);
        };
    };
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<input type="button" onclick="location.href='attendanceEventAction.do'" value="출첵">
			<h2>이벤트 목록</h2>
			<form id="search_form" action="eventList.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield== 1}">selected</c:if> >제목</option>
							<option value="2" <c:if test="${param.keyfield== 2}">selected</c:if> >내용</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
					<li>
						<label for="underway_btn">현재 진행중인 이벤트만 보기</label>
						<input type="checkbox" name="underway" id="underway_btn">				
					</li>
				</ul>
			</form>
			<div class="list-space align-right">
				<input type="button" value="이벤트 등록" onclick="location.href='insertEventForm.do'" 
				<c:if test="${user_auth != 9 || empty user_num}">style="display: none;"</c:if>>
				
				<input type="button" value="목록" onclick="location.href='eventList.do'">
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count == 0 }">
				<div class="result-display">
					표시할 게시글이 없습니다.
				</div>
			</c:if>
			<c:if test="${count > 0 }">
				<table>
					<tr>
						<th>이벤트 번호</th>
						<th>제목</th>
						<th>시작일</th>
						<th>종료일</th>
					</tr>
					<c:forEach var="event" items="${list}">
						<tr>
							<c:if test="${event.notice == 1}"><td><span style="color:blue;">공지사항</span></td></c:if>
							<c:if test="${event.notice == 0}"><td>${event.event_num}</td></c:if>
							<td><a href="eventDetail.do?event_num=${event.event_num}">${event.name}</a></td>
							<td>${event.start_date}</td>
							<td>${event.end_date}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="align-center">
					${page}
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>