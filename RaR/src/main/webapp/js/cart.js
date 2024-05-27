$(function(){
	$(document).on('click','.delete-btn',function(){
		//장바구니 번호
		let cart_num = $(this).attr('data-cartnum');
		//서버와 통신
		$.ajax({
			url:'cartDelete.do',
			type:'post',
			data:{cart_num:cart_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result == 'success'){
					alert('삭제 성공');
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
});