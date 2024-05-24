<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
	//회원 정보 등록 유효성 체크
	$('#register_form').submit(function(){
		const items = document.querySelectorAll('.input-check');
		for(let i=0;i<items.length;i++){
			if(items[i].value.trim()==''){
				const label = document.querySelector(
						              'label[for="'+items[i].id+'"]');
				alert(label.textContent + ' 항목은 필수 입력');
				items[i].value = '';
				items[i].focus();
				return false;
			}
			if(items[i].id == 'zipcode' 
					&& !/^[0-9]{5}$/.test($('#zipcode').val())){
				alert('우편번호를 입력하세요(숫자5자리)');
				$('#user_zipcode').val('').focus();
				return false;
			}
		}
	});
});
</script>
</head>
<body>
    <div class="page-main">
    
    </div>
</body>
</html>