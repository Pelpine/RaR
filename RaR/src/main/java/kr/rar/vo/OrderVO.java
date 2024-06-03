package kr.rar.vo;

import java.sql.Date;

public class OrderVO {
	private int order_num;				//주문번호
	private Date order_date;			//주문일
	private int order_status;			//배송상태
	private int order_points;			//적립 포인트
	private int pay_total;				//총상품금액
	private int pay_points;				//사용포인트
	private int pay_ship;				//배송비
	private int pay_payment;			//결제수단
	private String receive_name;		//수령인
	private String receive_post;		//우편번호
	private String receive_address1;	//주소
	private String receive_address2;	//상세주소
	private String receive_phone;		//전화번호	
	private String notice;				//남기실말씀
	private int user_num;				//회원번호
	
	private String id;					//회원아이디
	private String item_name;			//상품명
	
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public int getOrder_points() {
		return order_points;
	}
	public void setOrder_points(int order_points) {
		this.order_points = order_points;
	}
	public int getPay_total() {
		return pay_total;
	}
	public void setPay_total(int pay_total) {
		this.pay_total = pay_total;
	}
	public int getPay_points() {
		return pay_points;
	}
	public void setPay_points(int pay_points) {
		this.pay_points = pay_points;
	}
	public int getPay_ship() {
		return pay_ship;
	}
	public void setPay_ship(int pay_ship) {
		this.pay_ship = pay_ship;
	}
	public int getPay_payment() {
		return pay_payment;
	}
	public void setPay_payment(int pay_payment) {
		this.pay_payment = pay_payment;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getReceive_post() {
		return receive_post;
	}
	public void setReceive_post(String receive_post) {
		this.receive_post = receive_post;
	}
	public String getReceive_address1() {
		return receive_address1;
	}
	public void setReceive_address1(String receive_address1) {
		this.receive_address1 = receive_address1;
	}
	public String getReceive_address2() {
		return receive_address2;
	}
	public void setReceive_address2(String receive_address2) {
		this.receive_address2 = receive_address2;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
}