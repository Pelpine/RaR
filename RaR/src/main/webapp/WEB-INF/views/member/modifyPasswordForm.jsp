<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
	//아이디,비밀번호 유효성 체크
	$('#password_form').submit(function(){
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
		}//end of for
		
		if($('#password').val()!=$('#cpassword').val()){
			alert('새비밀번호와 새비밀번호 확인이 불일치');
			$('#password').val('').focus();
			$('#cpassword').val('');
			return false;
		}
	});//end of submit
	
	//새비밀번호 확인까지 한 후 다시 새비밀번호를 수정하려고 하면 새비밀번호 확인을
	//초기화
	$('#password').keyup(function(){
		$('#cpassword').val('');
		$('#message_cpassword').text('');
	});
	
	//새비밀번호와 새비밀번호 확인 일치 여부 체크
	$('#cpassword').keyup(function(){
		if($('#password').val()==$('#cpassword').val()){
			$('#message_cpassword').text('새비밀번호 일치');
		}else{
			$('#message_cpassword').text('');
		}
	});
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>비밀번호 수정</h2>
		<form id="password_form" action="modifyPassword.do"
		                                      method="post">
			<ul>
				<li>
					<label for="origin_password">현재 비밀번호</label>
					<input type="password" name="origin_password" 
					    id="origin_password"
					       maxlength="12" class="input-check">
				</li>
				<li>
					<label for="password">새비밀번호</label>
					<input type="password" name="password" id="password"
					       maxlength="12" class="input-check">
				</li>
				<li>
					<label for="cpassword">새비밀번호 확인</label>
					<input type="password" id="cpassword"
					       maxlength="12" class="input-check">
					<span id="message_cpassword"></span>       
				</li>
			</ul>    
			<div class="align-center">
				<input type="submit" value="비밀번호 수정">
				<input type="button" value="My페이지"
				    onclick="location.href='myPage.do'">
			</div>                                  
		</form>
	</div>
</div>
</body>
</html>






