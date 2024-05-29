$(function(){
	
	//목록 호출
	/*function selectList(){
		$.ajax({
			url:'cartItem.do',
			type:'post',
			dataType:'json',
			success:function(param){
				$('#output').empty();
					let head_output = '<tr>';
					head_output += '<th>상품정보</th>';
					head_output += '<th>등급</th>';
					head_output += '<th>판매가</th>';
					head_output += '<th>판매자</th>';
					head_output += '<th>장바구니 담기</th>';
					head_output += '</tr>';
					$('#output').append(head_output);
				
				$(param.list).each(function(index,item){
					let output = '<tr>';
					output += '<td>상품 정보</td>';
					output += '<td>등급</td>';
					output += '<td>판매가</td>';
					output += '<td>판매자</td>';
					output += '<td><input type="button" value="장바구니 담기" class="insertCart-btn"></td>';
					output += '</tr>';
					
					$('#output').append(output);
				});
				cartTotal();
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	//초기 함수 호출
	selectList();*/
});