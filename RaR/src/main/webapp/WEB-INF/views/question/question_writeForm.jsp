<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 작성</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm = document.getElementById('question_write_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const title = document.getElementById('question_title');
		if(title.value.trim()==''){
			alert('제목을 입력하세요');
			title.value = "";
			title.focus();
			return false;
		}
		const content = document.getElementById('content');
		if(content.value.trim()==''){
			alert('내용을 입력하세요');
			content.value = "";
			content.focus();
			return false;
		}
	};
};
</script>
</head>
<body>

</body>
</html>