<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>추천인 이메일 입력</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/khc.css" type="text/css">
<script type="text/javascript">
window.onload = function(){
	let idChecked = 0;
    const check = document.getElementById('check');
    check.onclick = function(){
        const user_email = $('#email').val(); 
        $.ajax({
            url: 'checkDuplicatedId.do',
            type: 'post',
            data: {user_email: user_email},
            dataType: 'json',
            success: function(param){
                if(param.result == 'idDuplicated'){
                    $('.check_email').text('추천인 등록이 가능합니다.');
                    idChecked=1;
                } else if(param.result == 'idNotFound'){
                    $('.check_email').text('해당 유저를 찾을 수 없습니다.');
                    idChecked=0;
                } else {
                    alert('유저 찾기 오류 발생');
                    idChecked=0;
                }
            },
            error: function(){
                alert('네트워크 오류발생');
                idChecked=0;
            }
        });
    }
    $('#email').keydown(function(){
		idChecked = 0;
		$('.check_email').text('');
	});//end of keydown
	
	$('#reference_form').submit(function(){
		if(idChecked == 0){
			alert('추천인 email을 입력하고 확인하세요.');
			return false;
		}
	});

}

</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>추천인 이벤트</h2>
		<div class="result-display">
			<div class="align-center">
				<form action="referenceEmailEvent.do" method="post" id="reference_form">
					<label for="email">추천인 EMAIL</label>
                   <input type="email" class="form-input"
                    name="email" id="email"
                    maxlength="50" autocomplete="off">
                    <input type="button" value="확인" id="check"> 
                    <p>
                    <span class="check_email"></span>
                    <p>
                    <input type="submit" value="등록"> 
				</form>
			</div>
		</div>
	</div>
</div>
<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>




