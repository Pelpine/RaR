$(function(){	
	$('#attendance_button').click(function(){
	    $.ajax({
	        url:'../event/attendanceEvent.do',
	        type:'post',
	        dataType:'json',
	        success: function(param) {
				location.reload(true);
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요');
				}else if(param.result == 'alreadyAttendance'){
					alert('오늘은 이미 출석체크 완료!');
				}else if(param.result == 'success'){
					alert('출석체크 완료. 50point 지급!');
				}else{
					alert('출석체크 오류 발생');
				}
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	});
});