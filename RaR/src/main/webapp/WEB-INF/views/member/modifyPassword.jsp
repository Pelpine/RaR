<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:if test="${check}">
	<script>
		alert('비밀번호를 수정했습니다.');
		location.href='myPage.do'
	</script>
</c:if>
<c:if test="${!check}">
	<script>
		alert('비밀번호를 정확하게 입력해주세요.');
		history.go(-1);
	</script>
</c:if>



