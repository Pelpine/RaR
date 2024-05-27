<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm = document.getElementById('write_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const items = 
			   document.querySelectorAll('.input-check');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				const label = document.querySelector(
						   'label[for="'+items[i].id+'"]');
				alert(label.textContent + ' 항목은 필수 입력');
				items[i].value = '';
				items[i].focus();
				return false;
			}
		}
	const start = document.getElementById('start_date');
	const end = document.getElementById('end_date');
	if(start.value>end.value){
		alert('이벤트 시작일과 종료일을 확인해주세요.');
		return false;
	}
	};
};
</script>
</head>
<body>
<div class="page-main">
	<h2>이벤트 수정</h2>
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<form id="write_form" action="updateEvent.do" method="post"
	                          enctype="multipart/form-data">
	    <input type="hidden" name="event_num" value="${event.event_num}">
		<ul>
			<li>
				<label for="name">이벤트명</label>
				<input type="text" name="name" id="name"
				  size="10" maxlength="50" class="input-check" value="${event.name}">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" name="content"
				    id="content" class="input-check">${event.content}</textarea>
			</li>
			<li>
				<label for="start-date">시작일</label>
     		  	<input type="date" id="start_date" name="start_date" value="${event.start_date}">
     		</li>
     		<li>
				<label for="end-date">종료일</label>
     		  	<input type="date" id="end_date" name="end_date" value="${event.end_date}">
     		</li>
			<li>
					<label for="filename">파일네임</label> 
					<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg"> 
					<c:if test="${!empty event.filename}">
							<div id="file_detail">
								<img src="${pageContext.request.contextPath}/upload/${event.filename}" width="100">
								<input type="button" value="파일 삭제" id="file_del">
							</div>
					</c:if>
					<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
					<script type="text/javascript">
					$('#file_del').click(function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							$.ajax({
								url:'deleteEventFile.do',
								type:'post',
								data:{event_num:${event.event_num}},
								dataType:'json',
								success:function(param){
									if(param.result == 'logout'){
											alert('로그인 후 사용하세요');
									}else if(param.result=='success'){
										 $('#file_detail').hide();
									}else if(param.result=='wrongAccess'){
										 alert('잘못된 접속입니다.');
									}else{
									 	alert('파일 삭제 오류 발생');
									}	
								},
								error: function(){
									alert('네트워크 오류 발생');
								}
							});
						}
                	}); 
					</script>
		</ul> 
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록"
			                onclick="location.href='list.do'">
		</div>                         
	</form>
	</div>
</div>
</body>
</html>

