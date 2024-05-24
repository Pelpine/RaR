<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<div class="page-main">
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <div class="content-main">
        <h2>로그인</h2>
        <form id="login_form" action="login.do" method="post">
            <ul>
                <li class="floating-label">
                   <input type="text" class="form-input"
                    placeholder="아이디" name="id" id="id"
                    maxlength="12" autocomplete="off">
                   <label for="id">아이디</label>
                </li>
                <li class="floating-label">
                   <input type="password" class="form-input"
                    placeholder="비밀번호">
                </li>
            </ul>
        </form>
    </div>
</div>
</body>
</html>