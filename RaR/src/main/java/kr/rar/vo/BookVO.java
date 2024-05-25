package kr.rar.vo;

import java.sql.Date;

public class BookVO {
	private int approval_id;
	private int status;
	private Date request_at;
	private Date approved_at;
	private int item_grade;
	private String bk_name;
	private String ad_comment;
	private int user_num;
	private int bk_num;
	
	public int getApproval_id() {
		return approval_id;
	}
	public void setApproval_id(int approval_id) {
		this.approval_id = approval_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getRequest_at() {
		return request_at;
	}
	public void setRequest_at(Date request_at) {
		this.request_at = request_at;
	}
	public Date getApproved_at() {
		return approved_at;
	}
	public void setApproved_at(Date approved_at) {
		this.approved_at = approved_at;
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
	public String getAd_comment() {
		return ad_comment;
	}
	public void setAd_comment(String ad_comment) {
		this.ad_comment = ad_comment;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getBk_num() {
		return bk_num;
	}
	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}
}
