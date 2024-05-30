package kr.rar.vo;

import java.sql.Date;

public class BoardReplyVO {
	private int re_num;
	private int user_num;
	private int board_num;
	private String content;
	private Date reg_date;
	private Date modify_date;
	private String user_ip;
	private int re_user_num;
	private BoardVO board;
	
	public BoardVO getBoard() {
		return board;
	}
	public void setBoard(BoardVO board) {
		this.board = board;
	}
	public int getRe_user_num() {
		return re_user_num;
	}
	public void setRe_user_num(int re_user_num) {
		this.re_user_num = re_user_num;
	}
	private String user_email;	
	
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public int getRe_num() {
		return re_num;
	}
	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}
}
