<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#login_form').submit(function(){
		if($('#user_email').val().trim()==''){
			alert('이메일을 입력하세요');
			$('#user_email').val('').focus();
			return false;;
		}
		if($('#password').val().trim()==''){
			alert('비밀번호를 입력하세요');
			$('#password').val('').focus();
			return false;;
		}
	});
});
</script>
</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header2.jsp"/>
    <div class="content-main">
        <h2>로그인</h2>
        <form id="login_form" action="login.do" method="post">
            <ul>
                <li class="floating-label">
                   <input type="email" class="form-input"
                    placeholder="이메일" name="user_email" id="user_email"
                    maxlength="50" autocomplete="off">
                   <label for="user_email">이메일</label>
                </li>
                <li class="floating-label">
                   <input type="password" class="form-input"
                    placeholder="비밀번호" name="password" id="password"
                    maxlength="12" autocomplete="off">
                   <label for="password">비밀번호</label>
                </li>
            </ul>
            <div class="align-center">
                <input type="submit" value="로그인">
                <input type="button" value="홈으로"
                 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
            </div>
        </form>
    </div>
</div>
</body>
</html>