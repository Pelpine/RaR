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
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>
        <div class="content-main">
            <h2>회원 가입</h2>
            <form id="register_form" action="registerUser.do" method="post">
                <ul>
                    <li>
                        <label for="user_email">이메일</label> <input type="text" name="user_email"
                         id="user_email" maxLength="50" class="intput-check">
                    </li>
                    <li>
                        <label for="user_name">이름</label> <input type="text"
						 name="user_name" id="user_name" maxlength="10" class="input-check">
					</li>
					<li>
					    <label for="password">비밀번호</label> <input type="password"
						 name="password" id="password" maxlength="12" class="input-check">
					</li>
					<li>
					    <label for="user_phone">전화번호</label> <input type="text"
						 name="user_phone" id="user_phone" maxlength="15" class="input-check">
					</li>
					<li>
					    <label for="user_zipcode">우편번호</label> <input type="text"
						 name="user_zipcode" id="user_zipcode" maxlength="5" autocomplete="off"
						 class="input-check"> <input type="button" value="우편번호 찾기"
						 onclick="execDaumPostcode()"></li>
					<li>
					    <label for="user_address1">주소</label> <input type="text"
						 name="user_address1" id="user_address1" maxlength="30" class="input-check">
					</li>
					<li>
					    <label for="user_address2">나머지 주소</label> <input type="text"
						 name="user_address2" id="user_address2" maxlength="30" class="input-check">
					</li>
                </ul>
                <div class="align-center">
                    <input type="submit" value="등록">
                    <input type="button" value="홈으로"
                     onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
                </div>
            </form>
            <!-- 다음 우편번호 API 시작 -->
			<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
			<div id="layer" 
			 style="display: none; position: fixed; overflow: hidden; z-index: 1; -webkit-overflow-scrolling: touch;">
			 <img src="//t1.daumcdn.net/postcode/resource/images/close.png">
			
			</div>
			
			<!-- 다음 우편번호 API 끝 -->
        </div>
    </div>
</body>
</html>



























