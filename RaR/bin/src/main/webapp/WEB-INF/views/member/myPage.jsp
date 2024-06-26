<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ysb.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#photo_btn').click(function(){
		$('#photo_choice').show();
		$(this).hide(); //수정 버튼 감추기
	});
	
	//이미지 미리 보기
	let photo_path = $('.my-photo').attr('src');//처음 화면에 보여지는 이미지 읽기
	$('#photo').change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
			//선택을 취소하면 원래 처음 화면으로 되돌림
			$('.my-photo').attr('src',photo_path);
			return;
		}
		
		if(my_photo.size > 1024*1024){
			alert(Math.round(my_photo.size/1024) 
					 + 'kbytes(1024kbytes까지만 업로드 가능)');
			$('.my-photo').attr('src',photo_path);
			$(this).val('');//선택한 파일 정보 지우기
			return;
		}
		//화면에 이미지 미리 보기
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$('.my-photo').attr('src',reader.result);
		};		
	});//end of change
	
	//이미지 전송
	$('#photo_submit').click(function(){
		if($('#photo').val()==''){
			alert('파일을 선택하세요');
			$('#photo').focus();
			return;
		}
		//파일 전송
		const form_data = new FormData();
		//업로드할 파일은 $('#photo').files[0]를 호출할 수 없음
		//$('#photo')[0].files[0] 또는
		//document.getElementById('photo').files[0]
		//형식으로 호출 가능
		form_data.append('user_photo',$('#photo')[0].files[0]);
		
		$.ajax({
			url:'updateMyPhoto.do',
			type:'post',
			data:form_data,
			dataType:'json',
			contentType:false, //데이터 객체를 문자열로 바꿀지 설정.true이면 일반문자
			processData:false, //해당 타입을 true로 하면 일반 text로 구분
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요');
				}else if(param.result == 'success'){
					alert('프로필 사진이 수정되었습니다.');
					//수정된 이미지 정보 저장
					photo_path = $('.my-photo').attr('src');
					$('#photo').val('');
					$('#photo_choice').hide();
					$('#photo_btn').show();//수정 버튼 표시
				}else{
					alert('파일 전송 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
	});//end of click
	
	//이미지 미리보기 취소
	$('#photo_reset').click(function(){
		//초기 이미지 표시
		$('.my-photo').attr('src',photo_path);//이미지 미리보기 전 이미지로 되돌리기
		$('#photo').val('');
		$('#photo_choice').hide();
		$('#photo_btn').show();//수정 버튼 표시
	});
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>회원정보</h2>
		<div class="mypage-div">
		    <h3>프로필 사진</h3>
			<ul>
				<li>
					<c:if test="${empty member.user_photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" 
					                   width="200" height="200" class="my-photo">
					</c:if>
					<c:if test="${!empty member.user_photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.user_photo}" 
					                   width="200" height="200" class="my-photo">
					</c:if>
				</li>
				<li>
					<div class="align-center">
						<input type="button" value="프로필 수정"
						                     id="photo_btn">
					</div>
					<div id="photo_choice" style="display:none;">
						<input type="file" id="photo"
						          accept="image/gif,image/png,image/jpeg">
						<input type="button" value="전송" id="photo_submit">
						<input type="button" value="취소" id="photo_reset">          
					</div>
				</li>
			</ul>
			<ul>
				<li>이름 : ${member.user_name}</li>
				<li>이메일 : ${member.user_email}</li>
				<c:choose>
				<c:when test="${empty member.user_point}">
						<li>포인트 : 0</li>
				</c:when>
				<c:when test="${!empty member.user_point}">
						<li>포인트 : ${member.user_point}</li>
				</c:when>
				</c:choose>
			</ul> 
			<div>
			<input type="button" value="회원정보 수정" 
			               onclick="location.href='modifyUserForm.do'">
			<input type="button" value="비밀번호 수정" 
			               onclick="location.href='modifyPasswordForm.do'">
			<input type="button" value="회원탈퇴" 
			               onclick="location.href='deleteUserForm.do'">
			</div>

	</div>
	<div>
		<input type="button" value="최근 구매 내역" 
		onclick="">
	</div>
	<div>
		<input type="button" value="내가 쓴 게시물"
		 onclick="location.href='${pageContext.request.contextPath}/board/myPostingList.do?user_num=${member.user_num}'">
	</div>
	<div>
		<input type="button" value="내가 쓴 댓글" 
		onclick="location.href='${pageContext.request.contextPath}/board/myReplyList.do?user_num=${member.user_num}'">
	</div>
	<div>
		<input type="button" value="내가 좋아요 누른 글" 
		onclick="location.href='${pageContext.request.contextPath}/board/myFavList.do?user_num=${member.user_num}'">
	</div>
	<div>
		<input type="button" value="내가 쓴 QnA" onclick="location.href=">
	</div>
	</div>
</div>
</body>
</html>







