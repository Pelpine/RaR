package kr.rar.vo;

public class GenreVO {

	private int bg_num;
	private String bg_title;
	private int user_num;

	
	
	public GenreUserVO genreuserVO;
	
	public GenreUserVO getGenreuserVO() {
		return genreuserVO;
	}
	public void setGenreuserVO(GenreUserVO genreuserVO) {
		this.genreuserVO = genreuserVO;
	}
	public int getBg_num() {
		return bg_num;
	}
	public void setBg_num(int bg_num) {
		this.bg_num = bg_num;
	}
	public String getBg_title() {
		return bg_title;
	}
	public void setBg_title(String bg_title) {
		this.bg_title = bg_title;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	
}
