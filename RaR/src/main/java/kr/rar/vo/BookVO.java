package kr.rar.vo;

public class BookVO {
	private int bk_num;//책 번호
	private String bk_name;//책이름
	private String bk_writer;//작가
	private String bk_publisher;//출판사
	private int bk_price;//정가
	private String bk_img;//도서사진
	private String bk_genre;//장르
	private String bk_isbn;
	private String bk_description;
	
	private ItemVO itemVO;
	
	public ItemVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}
	public String getBk_isbn() {
		return bk_isbn;
	}
	public void setBk_isbn(String bk_isbn) {
		this.bk_isbn = bk_isbn;
	}
	public String getBk_description() {
		return bk_description;
	}
	public void setBk_description(String bk_description) {
		this.bk_description = bk_description;
	}
	public int getBk_num() {
		return bk_num;
	}
	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}
	public String getBk_name() {
		return bk_name;
	}
	public void setBk_name(String bk_name) {
		this.bk_name = bk_name;
	}
	public String getBk_writer() {
		return bk_writer;
	}
	public void setBk_writer(String bk_writer) {
		this.bk_writer = bk_writer;
	}
	public String getBk_publisher() {
		return bk_publisher;
	}
	public void setBk_publisher(String bk_publisher) {
		this.bk_publisher = bk_publisher;
	}
	public int getBk_price() {
		return bk_price;
	}
	public void setBk_price(int bk_price) {
		this.bk_price = bk_price;
	}
	public String getBk_img() {
		return bk_img;
	}
	public void setBk_img(String bk_img) {
		this.bk_img = bk_img;
	}
	public String getBk_genre() {
		return bk_genre;
	}
	public void setBk_genre(String bk_genre) {
		this.bk_genre = bk_genre;
	}
}
