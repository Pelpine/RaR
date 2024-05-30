package kr.rar.vo;

import java.sql.Date;

public class GenreUserVO {
private int bgu_num;
private String bgu_content;
private Date bgu_date;
private Date bgu_redate;
private int bg_num;
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
public Date getBgu_date() {
	return bgu_date;
}
public void setBgu_date(Date bgu_date) {
	this.bgu_date = bgu_date;
}
public Date getBgu_redate() {
	return bgu_redate;
}
public void setBgu_redate(Date bgu_redate) {
	this.bgu_redate = bgu_redate;
}
public int getBg_num() {
	return bg_num;
}
public void setBg_num(int bg_num) {
	this.bg_num = bg_num;
}

}
