package kr.rar.vo;

import java.sql.Date;

public class OrderVO {
	private int order_num;		//주문번호
	private int user_num;		//회원번호
	private int item_num;		//상품번호
	private int total_amount;	//총 금액
	private Date order_date;	//주문일
	private String order_name;	//수령인
	private int zipcode;		//우편번호
	private String address1;	//주소
	private String address2;	//상세주소
	private int order_pay;		//결제방식
	private int status;			//주문 상태 1. 입금대기 2. 입금확인 3. 배송준비 4. 배송중 5. 배송완료
	
	
	
}
