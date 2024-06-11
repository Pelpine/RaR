package kr.rar.vo;

import java.sql.Date;

public class RefundVO {
	private int refund_num;
	private int order_num;
	private int item_num;
	private int reason;
	private String reason_other;
	private Date request_date;
	private Date refund_date;
	private int collect_point;
	private int refund_price;
	private int status;
	private String bank;
	private String account;
	private int user_num;
	public int getRefund_num() {
		return refund_num;
	}
	public void setRefund_num(int refund_num) {
		this.refund_num = refund_num;
	}
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	public String getReason_other() {
		return reason_other;
	}
	public void setReason_other(String reason_other) {
		this.reason_other = reason_other;
	}
	public Date getRequest_date() {
		return request_date;
	}
	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}
	public Date getRefund_date() {
		return refund_date;
	}
	public void setRefund_date(Date refund_date) {
		this.refund_date = refund_date;
	}
	public int getCollect_point() {
		return collect_point;
	}
	public void setCollect_point(int collect_point) {
		this.collect_point = collect_point;
	}
	public int getRefund_price() {
		return refund_price;
	}
	public void setRefund_price(int refund_price) {
		this.refund_price = refund_price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	
	
}
