package kr.rar.vo;

import java.sql.Date;

public class OrderVO {
	//주문정보 테이블
	private int order_num;		//주문번호
	private int user_num;		//회원번호
	private int item_num;		//상품번호
	private int bk_num;			//도서번호
	
	private int total_amount;	//총 금액
	private Date order_date;	//주문일
	private String order_name;	//수령인
	private int zipcode;		//우편번호
	private String address1;	//주소
	private String address2;	//상세주소
	private int order_pay;		//결제방식
	private int status;			//주문 상태 1. 입금대기 2. 입금확인 3. 배송준비 4. 배송중 5. 배송완료
	
	//회원 테이블 참조 변수(상세포함)
	private String user_name;	//회원명
	private int user_point;		//포인트
	
	//상품테이블 참조 변수
	private int item_price;		//판매가				
	private int item_grade;		//도서 상태
	
	//도서테이블 참조 변수
	private String bk_name;		//도서명
	private String bk_img;		//도서 사진
	private int bk_price;		//정가
	
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getBk_num() {
		return bk_num;
	}
	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public int getOrder_pay() {
		return order_pay;
	}
	public void setOrder_pay(int order_pay) {
		this.order_pay = order_pay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getUser_point() {
		return user_point;
	}
	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	public int getItem_grade() {
		return item_grade;
	}
	public void setItem_grade(int item_grade) {
		this.item_grade = item_grade;
	}
	public String getBk_name() {
		return bk_name;
	}
	public void setBk_name(String bk_name) {
		this.bk_name = bk_name;
	}
	public String getBk_img() {
		return bk_img;
	}
	public void setBk_img(String bk_img) {
		this.bk_img = bk_img;
	}
	public int getBk_price() {
		return bk_price;
	}
	public void setBk_price(int bk_price) {
		this.bk_price = bk_price;
	}
}