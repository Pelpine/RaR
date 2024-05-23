package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private int user_num;//회원 번호
	private String user_name;//이름
	private String user_email;//이메일(아이디)
	private int user_auth;//회원등급
	
	private String user_password;//비밀번호
	private String user_phone;//전화번호

	private String user_zipcode;//우편번호
	private String user_address1;//주소
	private String user_address2;//상세주소
	private String user_photo;//프로필 사진
	private Date user_date;//가입일
	private String user_ip;//IP
	private int user_point;//포인트
	private String user_comment;//관리자 코멘트
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPasswd) {
		//회원 등급(user_auth) : 0탈퇴회원,1정지회원,2일반회원,9관리자
		if(user_auth> 1 && user_password.equals(userPasswd)) {
			return true;
		}
		return false;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public int getUser_auth() {
		return user_auth;
	}

	public void setUser_auth(int user_auth) {
		this.user_auth = user_auth;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_zipcode() {
		return user_zipcode;
	}

	public void setUser_zipcode(String user_zipcode) {
		this.user_zipcode = user_zipcode;
	}

	public String getUser_address1() {
		return user_address1;
	}

	public void setUser_address1(String user_address1) {
		this.user_address1 = user_address1;
	}

	public String getUser_address2() {
		return user_address2;
	}

	public void setUser_address2(String user_address2) {
		this.user_address2 = user_address2;
	}

	public String getUser_photo() {
		return user_photo;
	}

	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}

	public Date getUser_date() {
		return user_date;
	}

	public void setUser_date(Date user_date) {
		this.user_date = user_date;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public int getUser_point() {
		return user_point;
	}

	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}

	public String getUser_comment() {
		return user_comment;
	}

	public void setUser_comment(String user_comment) {
		this.user_comment = user_comment;
	}

	@Override
	public String toString() {
		return "MemberVO [user_num=" + user_num + ", user_name=" + user_name + ", user_email=" + user_email
				+ ", user_auth=" + user_auth + ", user_password=" + user_password + ", user_phone=" + user_phone
				+ ", user_zipcode=" + user_zipcode + ", user_address1=" + user_address1 + ", user_address2="
				+ user_address2 + ", user_photo=" + user_photo + ", user_date=" + user_date + ", user_ip=" + user_ip
				+ ", user_point=" + user_point + ", user_comment=" + user_comment + "]";
	}
}
