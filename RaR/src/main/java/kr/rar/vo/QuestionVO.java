package kr.rar.vo;

import java.sql.Date;

public class QuestionVO {
	private int question_num;
	private String question_title;
	private String question_content;
	private Date question_reg_date;
	private Date question_modify_date;
	private String question_filename;
	private int user_num;
	private int user_auth;
	private String user_ip;
	
	
	private String user_email;
	private String user_photo;
	
	public int getQuestion_num() {
		return question_num;
	}
	public void setQuestion_num(int question_num) {
		this.question_num = question_num;
	}
	public String getQuestion_title() {
		return question_title;
	}
	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public Date getQuestion_reg_date() {
		return question_reg_date;
	}
	public void setQuestion_reg_date(Date question_reg_date) {
		this.question_reg_date = question_reg_date;
	}
	public Date getQuestion_modify_date() {
		return question_modify_date;
	}
	public void setQuestion_modify_date(Date question_modify_date) {
		this.question_modify_date = question_modify_date;
	}
	public String getQuestion_filename() {
		return question_filename;
	}
	public void setQuestion_filename(String question_filename) {
		this.question_filename = question_filename;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getUser_auth() {
		return user_auth;
	}
	public void setUser_auth(int user_auth) {
		this.user_auth = user_auth;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}
}
