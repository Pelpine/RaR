<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
	//아이디,비밀번호 유효성 체크
	$('#delete_form').submit(function(){
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
			alert('비밀번호와 비밀번호 확인 불일치');
			$('#password').val('').focus();
			$('#cpassword').val('');
			return false;
		}
	});//end of submit
	
	//비밀번호 확인까지 한 후 다시 비밀번호를 수정하려고 하면 새비밀번호 확인을
	//초기화
	$('#password').keyup(function(){
		$('#cpassword').val('');
		$('#message_cpassword').text('');
	});
	
	//비밀번호와 비밀번호 확인 일치 여부 체크
	$('#cpassword').keyup(function(){
		if($('#password').val()==$('#cpassword').val()){
			$('#message_cpassword').text('비밀번호 일치');
		}else{
			$('#message_cpassword').text('비밀번호 불일치');
		}
	});
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="page-main">
        <div class="content-main">
            <h2>회원탈퇴</h2>
            <form id="delete_form" action="deleteUser.do" method="post">
                <ul>
                    <li>
                        <label for="user_email">이메일</label>
                        <input type="email" name="user_email" id="user_email"
                         maxlength="50" autocomplete="off" class="input-check">
                    </li>
                    <li>
                        <label for="password">비밀번호</label>
                        <input type="password" name="password" id="password"
                         maxlength="12" class="input-check">
                    </li>
                    <li>
                        <label for="cpassword">비밀번호 확인</label>
                        <input type="password" name="cpassword" id="cpassword"
                         maxlength="12" class="input-check">
                        <span id="message_cpassword"></span>
                    </li>
                </ul>
                <div class="align-center">
                    <input type="submit" value="회원탈퇴">
                    <input type="button" value="MY페이지"
                           onclick="location.href='myPage.do'">
                </div>
            </form>
        </div>
    </div>
</body>
</html>