$(function(){
	//장바구니에 담기
	
	$(document).on('submit','.insertCart',function(){
		let form_data = $(this).serialize();
		
	    $.ajax({
	        url:'../cart/cartWrite.do',
	        type:'post',
	        data:form_data,
	        dataType:'json',
	        success: function(param) {
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
	});

/*AJAX로 목록호출 하려했으나 실패.. 일단 코드 남겨둠
	//목록 호출
	function selectList(){
		$.ajax({
			url:'bookdetail.do',
			type:'post',
			data:{bk_num:bk_num},
			dataType:'json',
			success:function(param){
				$('#output').empty();
					let head_output = '<tr>';
					head_output += '<th>상품정보</th>';
					head_output += '<th>등급</th>';
					head_output += '<th>판매가</th>';
					head_output += '<th>장바구니 담기</th>';
					head_output += '</tr>';
					$('#output').append(head_output);
				
				$(param.list).each(function(index,item){
					let output = '<tr>';
					output += '<td>'
						    + '<img src="' + item.bookVO.bk_img + '" width="60">'
						    + '<img src="' + contextPath + '/upload/' + item.item_img + '" width="60">'
						    + '</td>';
					output += '<td>' + item.item_grade + '</td>';
					output += '<td>' + item.item_price + '</td>';
					output += '<td><input type="button" value="장바구니 담기" class="insertCart-btn"></td>';
					output += '</tr>';
					
					$('#output').append(output);
				});
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	//초기 함수 호출
	selectList();
*/
});