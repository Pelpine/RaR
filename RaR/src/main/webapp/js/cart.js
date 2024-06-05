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
					head_output += '<th><input type="checkbox" id="selectAll" checked></th>';
					head_output += '<th>상품정보</th>';
					head_output += '<th>가격</th>';
					head_output += '<th>상태</th>';
					head_output += '<th>삭제</th>';
					head_output += '</tr>';
					$('#output').append(head_output);
				
				$(param.list).each(function(index,item){
					let output = '<tr>';
					//체크박스
					output += '<td><input type="checkbox" data-cartnum="' + item.cart_num + '" class="selectCheck" checked></td>';
					//상품정보 : 책 이미지, 이름
					output += '<td>'
							+ '<a href="../book/booksdetail.do?bk_num=' + item.bk_num + '">'
							+ '<img src="' + item.bookVO.bk_img + '" width="60">' + item.bookVO.bk_name + '</a>'
							+ '</td>';
					//상품 가격 : 정가, 판매가, 예상 적립포인트
					output += '<td class="item_list_price">'
							+ '<span class="item_bk_price">정가 : ' + item.bookVO.bk_price.toLocaleString() + '원</span><br>'
							+ '판매가 : <span id="item_price_' + item.cart_num + '" class="item_item_price">' + item.itemVO.item_price.toLocaleString() + '</span>원<br>'
							+ '예상 적립포인트 : ' + Math.floor(item.itemVO.item_price * 0.01).toLocaleString() + 'p'
							+ '</td>';
					//책 등급 1:상, 2:중, 3:하
					if(item.itemVO.item_grade==1) output += '<td><span class="item_grade1">상</span></td>';
					else if(item.itemVO.item_grade==2) output += '<td><span class="item_grade2">중</span></td>';
					else if(item.itemVO.item_grade==3) output += '<td><span class="item_grade3">하</span></td>';
					output += '<td><input type="button" data-cartnum="' + item.cart_num + '" value="삭제" class="delete-btn"></td>';
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
	    cartTotal();
	});
	
	//장바구니 개별 삭제
	$(document).on('click','.delete-btn',function(){
		//장바구니 번호
		let cart_num = $(this).attr('data-cartnum');
		deleteCartItem(cart_num);
	});
	//장바구니 선택 삭제
	$(document).on('click', '#selectAll-btn', function () {
		if(confirm('선택한 상품을 모두 삭제하시겠습니까?')){
		    $('.selectCheck:checked').each(function () {
		        let cart_num = $(this).attr('data-cartnum');
		        deleteCartItem(cart_num);
		    });			
		}
	});
	//선택한 상품 총 구매비용 계산
	$(document).on('change', '.selectCheck',function() {
		cartTotal();
	});
	//구매비용 계산 함수
	function cartTotal() {
	    let totalCount = 0;
	    let totalPrice = 0;
	    let ship = 0; // 배송비
	    let totalPayment = 0;
	    let totalPoints = 0;
		
	    // 각 상품별 정보를 가져와서 계산
	    $('.selectCheck:checked').each(function() {
	        let cartNum = $(this).attr('data-cartnum');
	        let item_price = parseInt($('#item_price_' + cartNum).text().replace(/,/g,''));
        
			//상품 개수
			totalCount++;
			//상품 금액
	        totalPrice += item_price;
			
	    });
		
		//배송비	
        if (totalPrice <= 0) ship = 0;
        else if(totalPrice < 30000) ship = 4000;
		//포인트
        totalPoints = Math.floor(totalPrice * 0.01); // 총 주문 금액의 1%를 적립
		//총 상품 금액
	    totalPayment = totalPrice + ship;
	
	    // 결과를 화면에 업데이트
	    $('#cart_total td:eq(0)').text(totalCount + '개');
	    $('#cart_total td:eq(1)').text(totalPrice.toLocaleString() + '원');
	    $('#cart_total td:eq(2)').text(ship.toLocaleString() + '원');
	    $('#cart_total td:eq(3)').text(totalPayment.toLocaleString() + '원');
	    $('#cart_total td:eq(4)').text(totalPoints.toLocaleString() + 'p');
	    
	    //전송할 데이터 값 입력
	    $('#totalCount').val(totalCount);
	    $('#totalPrice').val(totalPrice);
	    $('#ship').val(ship);
	    $('#totalPayment').val(totalPayment);
	    $('#totalPoints').val(totalPoints);
	}
	
	//
	$(document).on('submit','#cart_order',function(){
		$('#selectedItems').empty();
		
		$('.selectCheck:checked').each(function() {
			let selectedItem = '<input type="hidden" name="selectedCartNums" value="' + $(this).attr('data-cartnum') + '">';
        	$('#selectedItems').append(selectedItem);
		});
	});
	//초기 함수 호출
	selectList();
});