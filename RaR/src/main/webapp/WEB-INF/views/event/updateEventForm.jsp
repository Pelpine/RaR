
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
    const myForm = document.getElementById('write_form');
    //이벤트 연결
    myForm.onsubmit=function(){
        const items = document.querySelectorAll('.input-check');
        for(let i=0;i<items.length;i++){
            if(items[i].value.trim()==''){
                const label = document.querySelector('label[for="'+items[i].id+'"]');
                alert(label.textContent + ' 항목은 필수 입력');
                items[i].value = '';
                items[i].focus();
                return false;
            }
        }

        const start = document.getElementById('start_date');
        const end = document.getElementById('end_date');
        if(start.value > end.value){
            alert('이벤트 시작일과 종료일을 확인해주세요.');
            return false;
        }

        const notice = document.getElementById('notice');
        // 체크박스가 체크되지 않은 경우 0으로 설정
        if(notice.checked){
            notice.value=1;
        }else {
            notice.value=0;
        }
    }
        var file = document.getElementById('banner');
           file.onchange=function(){      	       
        	var reader = new FileReader(); 
            reader.readAsDataURL(file.files[0]); 
            reader.onload = function(){              
        		var image = new Image(); 
                image.src = reader.result; 
                image.onload=function(){
                     //이미지 파일의 넓이와 높이            
        		if (this.width <= 850 || this.width >= 1000 || this.height <= 250 || this.height >= 350){
        			alert('배너의 크기는 넓이 850~ 1000, 높이 250~350 이어야 합니다.');
        			file.value='';
        		} 
                }; 
             };                         
        };
    };
</script>
</head>
<body>
	<div class="page-main">
	
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>이벤트 수정</h2>
			<form id="write_form" action="updateEvent.do" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="event_num" value="${event.event_num}">
				<ul>
					<li><label for="notice">공지사항 등록</label> <input type="checkbox"
						name="notice" id="notice" <c:if test="${event.notice == 1}">checked</c:if>></li>
					<li><label for="name">이벤트명</label> <input type="text"
						name="name" id="name" size="10" maxlength="50" class="input-check"
						value="${event.name}"></li>
					<li><label for="content">내용</label> <textarea rows="5"
							cols="40" name="content" id="content" class="input-check">${event.content}</textarea>
					</li>
					<li><label for="start-date">시작일</label> <input type="date"
						id="start_date" name="start_date" value="${event.start_date}">
					</li>
					<li><label for="end-date">종료일</label> <input type="date"
						id="end_date" name="end_date" value="${event.end_date}"></li>
					<li><label for="filename">이미지</label> <input type="file"
						name="filename" id="filename"
						accept="image/gif,image/png,image/jpeg"> <br> <img
						src="${pageContext.request.contextPath}/upload/${event.filename}"
						width="100" style="margin-left: 125px;"></li>
					<li><label for="banner">이벤트 배너</label> <input type="file"
						name="banner" id="banner" accept="image/gif,image/png,image/jpeg">
						(배너 사이즈 : 850~1000x250~350)</li>
				</ul>
				<div class="align-center">
					<input type="submit" value="등록"> <input type="button"
						value="목록" onclick="location.href='eventList.do'">
				</div>
			</form>
		</div>
	</div>
	<footer>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</footer>
</body>
</html>

