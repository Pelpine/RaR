<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${result_title}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/khc.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${result_title}</h2>
		<div class="result-display">
			<div class="align-center3">
				${result_msg}<br>
				누군가의 추천으로 Read & Renew를 찾아오셨나요?
				<p>
				<input type="button" value="추천인 없음"
				   onclick="location.href='${result_url1}'">
				<input type="button" value="추천인 있음"
				   onclick="location.href='${result_url2}'">
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>

