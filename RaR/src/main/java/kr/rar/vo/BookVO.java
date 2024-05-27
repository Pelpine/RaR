package kr.rar.vo;

import java.sql.Date;

public class BookVO {
	private int approval_id;	//책 코드
	private int status;			//승인상태
	private Date request_at;	//요청날짜
	private Date approved_at;	//승인 확정일
	private int item_grade;		//상품 상태
	private String bk_name;		//도서명
	private String ad_comment;	//코맨트
	private int user_num;		//유저코드
	private String author;		//저자
	private String pubDate;		//책등록일
	private String coverUrl;	//커버
	private String categoryName; //장르
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
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
	
}
