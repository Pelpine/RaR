<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${result_title}</title>
 <script type="text/javascript">
 window.onload = function() {
        history.pushState(null, null, location.href);
        window.onpopstate = function () {
            history.go(1);
        };
 };
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>${result_title}</h2>
		<div class="result-display">
			<div class="align-center">
				${result_msg}<br>
				<p>
				<input type="button" value="확인"
				   onclick="location.href='${result_url}'">
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>









