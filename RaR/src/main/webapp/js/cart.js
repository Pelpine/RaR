$(function(){
	//목록 호출
	function selectList(){
		$.ajax({
			url:'cartItem.do',
			type:'post',
			dataType:'json',
			success:function(param){
				$('#output').empty();
					let head_output = '<tr>';
					head_output += '<th><input type="checkbox" id="selectAll"></th>';
					head_output += '<th>상품정보</th>';
					head_output += '<th>가격</th>';
					head_output += '<th>상태</th>';
					head_output += '<th>삭제</th>';
					head_output += '</tr>';
					$('#output').append(head_output);
				$(param.list).each(function(index,item){
					let output = '<tr>';
					output += '<td><input type="checkbox" data-cartnum="' + item.cart_num + '" class="selectCheck"></td>';
					output += '<td><img src="../images/' + item.bookVO.bk_img + '" width="80">' + item.bookVO.bk_name + '</td>';
					output += '<td> 정가 : ' + item.bookVO.bk_price
							+ '<br> 판매가 : ' + item.itemVO.item_price + '</td>';
					output += '<td>' + item.itemVO.item_grade + '</td>';
					output += '<td><input type="button" data-cartnum="' + item.cart_num + '" value="삭제" class="delete-btn"></td>';
					output += '</tr>';
					
					$('#output').append(output);
				});
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//장바구니 삭제 함수
	function deleteCartItem(cart_num){
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
					selectList();
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
		//전체 선택
	$(document).on('change', '#selectAll', function() {
	    if ($(this).prop('checked')) {
	        $('.selectCheck').prop('checked', true);
	    } else {
	        $('.selectCheck').prop('checked', false);
	    }
	});
	
	//장바구니 개별 삭제
	$(document).on('click','.delete-btn',function(){
		//장바구니 번호
		let cart_num = $(this).attr('data-cartnum');
		deleteCartItem(cart_num);
	});
	//장바구니 선택 삭제
	$(document).on('click', '#selectAll-btn', function () {
	    $('.selectCheck:checked').each(function () {
	        let cart_num = $(this).attr('data-cartnum');
	        deleteCartItem(cart_num);
	    });
	});
	
	selectList();
});