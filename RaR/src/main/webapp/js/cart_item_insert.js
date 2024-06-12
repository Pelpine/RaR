$(function(){
	//장바구니에 담기
	
	$(document).on('submit','.insertCart',function(event){
		let form_data = $(this).serialize();
		
	    $.ajax({
	        url:'../cart/cartWrite.do',
	        type:'post',
	        data:form_data,
	        dataType:'json',
	        success: function(param) {
				console.log(param.result)
	           	if(param.result == 'logout'){
					alert('로그인 후 사용하세요');
				}else if(param.result == 'noSale'){
					alert('해당 상품이 현재 판매중이 아닙니다.');
				}else if(param.result == 'duplicated'){
					alert('상품이 이미 장바구니에 담겨져있습니다.');
				}else if(param.result == 'success'){
					alert('장바구니에 상품을 담았습니다.');
				}else{
					alert('장바구니 담기 오류');
				}
	        },
	        error: function() {
	            alert('네트워크 오류 발생');
	        }
	    });
	    event.preventDefault();
	});
});