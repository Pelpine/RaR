<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 작성</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div>
			<form action="reviewinsert.do" method="post" name="review_form">
			<c:forEach var="re" items="${order}"></c:forEach>
			
				<ul class="form-list">
					<li class="form-item form-item-inline">
						<label for="bk_name">별점</label>
						<input type="text" id="bk_name" name="bk_name">
					</li>
					<li class="form-item form-item-inline">
						<label for="photo">상품사진</label>
						<input type="file" name="photo" class="input-check" id="photo" accept="image/gif,image/png,image/jpeg"></li>
					<li class="form-item">
						<div>코멘트</div>
						<textarea rows="5" cols="30" id="comment" name="comment"></textarea>
					</li>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>