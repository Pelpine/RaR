package kr.rar.vo;

import java.sql.Date;

public class GenreUserVO {
private int bgu_num;
private String bgu_content;
private String bgu_date;
private String bgu_redate;
private int bg_num;
private int user_num;

public String getBgu_date() {
	return bgu_date;
}
public void setBgu_date(String bgu_date) {
	this.bgu_date = bgu_date;
}
public String getBgu_redate() {
	return bgu_redate;
}
public void setBgu_redate(String bgu_redate) {
	this.bgu_redate = bgu_redate;
}
public int getBgu_num() {
	return bgu_num;
}
public void setBgu_num(int bgu_num) {
	this.bgu_num = bgu_num;
}
public String getBgu_content() {
	return bgu_content;
}
public void setBgu_content(String bgu_content) {
	this.bgu_content = bgu_content;
}
public int getBg_num() {
	return bg_num;
}
public void setBg_num(int bg_num) {
	this.bg_num = bg_num;
}
public void setUser_num(int user_num) {
    this.user_num = user_num;
}

public int getUser_num() {
    return user_num;
}
private String user_email;	


public String getUser_email() {
	return user_email;
}
public void setUser_email(String user_email) {
	this.user_email = user_email;
}
}
