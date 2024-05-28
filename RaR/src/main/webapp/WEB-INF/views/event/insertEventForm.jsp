<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	const notice = document.getElementById('notice');
    // 체크박스가 체크되지 않은 경우 0으로 설정
    if(notice.checked){
    	notice.value=1;
    }else {
    	notice.value=0;
    }
	};
};
</script>
</head>
<body>
<div class="page-main">
	<h2>이벤트 등록</h2>
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<form id="write_form" action="insertEvent.do" method="post"
	                          enctype="multipart/form-data">
		<ul>
			<li>
				<label for="name">이벤트명</label>
				<input type="text" name="name" id="name"
				  size="10" maxlength="50" class="input-check">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" name="content"
				    id="content" class="input-check"></textarea>
			</li>
			<li>
				<label for="start-date">시작일</label>
     		  	<input type="date" id="start_date" name="start_date">
     		</li>
     		<li>
				<label for="end-date">종료일</label>
     		  	<input type="date" id="end_date" name="end_date">
     		</li>
			<li>
				<label for="filename">이벤트 사진 첨부</label>
				<input type="file" name="filename" id="filename"
				  class="input-check" 
				  accept="image/gif,image/png,image/jpeg">
			</li>
			<li>
				<label for="notice">공지사항 등록 여부</label>
				<input type="checkbox" name="notice" id="notice" value="0">
			</li>
		</ul> 
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록"
			                onclick="location.href='eventList.do'">
		</div>                         
	</form>
	</div>
</div>
</body>
</html>


