package kr.rar.vo;

public class OrderDetailVO {
	private int detail_num;		//고유번호
	private int item_num;		//상품번호
	private String item_name;	//상품명
	private String bk_img;		//책사진
	private int bk_price;		//정가
	private int item_price;		//판매가
	private int item_grade;		//상품상태
	private int order_num;		//주문번호
	
	public int getDetail_num() {
		return detail_num;
	}
	public void setDetail_num(int detail_num) {
		this.detail_num = detail_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	
	
}