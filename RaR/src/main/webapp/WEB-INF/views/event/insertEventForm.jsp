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
	};
};
</script>
</head>
<body>
<div class="page-main">
	<h2>이벤트 등록</h2>
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
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
     		  	<input type="date" id="start-date" name="start-date">
     		</li>
     		<li>
				<label for="end-date">종료일</label>
     		  	<input type="date" id="end-date" name="end-date">
     		</li>
			<li>
				<label for="filename">이벤트 사진 첨부</label>
				<input type="file" name="filename" id="filename"
				  class="input-check" 
				  accept="image/gif,image/png,image/jpeg">
			</li>
		</ul> 
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록"
			                onclick="location.href='list.do'">
		</div>                         
	</form>
</div>
</body>
</html>


