package kr.rar.vo;

import java.sql.Date;

public class ItemVO {
	private int item_num;		//상품번호
	private int item_price;		//판매가
	private int item_grade;		//도서 상태
	private String item_img;	//중고 도서 사진
	private int bk_num;			//도서번호
	private int approval_id;	//등록요청코드
	private int item_status;	//판매 상태 (1:판매중,2:판매완료,3:판매정지)
	private Date reg_date;		//등록 날짜
	private int max_price;
	private int min_price;
	
	public int getMax_price() {
		return max_price;
	}
	public void setMax_price(int max_price) {
		this.max_price = max_price;
	}
	public int getMin_price() {
		return min_price;
	}
	public void setMin_price(int min_price) {
		this.min_price = min_price;
	}
	
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	//도서
	private BookVO bookVO;
	//도서 승인
	private BookApprovalVO bookApprovalVO;
	//주문정보
	private OrderDetailVO orderDetailVO;
	
	public OrderDetailVO getOrderDetailVO() {
		return orderDetailVO;
	}
	public void setOrderDetailVO(OrderDetailVO orderDetailVO) {
		this.orderDetailVO = orderDetailVO;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
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
	public String getItem_img() {
		return item_img;
	}
	public void setItem_img(String item_img) {
		this.item_img = item_img;
	}
	public int getBk_num() {
		return bk_num;
	}
	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}
	public int getApproval_id() {
		return approval_id;
	}
	public void setApproval_id(int approval_id) {
		this.approval_id = approval_id;
	}
	public int getItem_status() {
		return item_status;
	}
	public void setItem_status(int item_status) {
		this.item_status = item_status;
	}
	public BookVO getBookVO() {
		return bookVO;
	}
	public void setBookVO(BookVO bookVO) {
		this.bookVO = bookVO;
	}
	public BookApprovalVO getBookApprovalVO() {
		return bookApprovalVO;
	}
	public void setBookApprovalVO(BookApprovalVO bookApprovalVO) {
		this.bookApprovalVO = bookApprovalVO;
	}
}