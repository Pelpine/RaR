package kr.rar.vo;

public class ItemVO {
	private int item_num;		//상품번호
	private int item_price;		//판매가
	private int item_grade;		//도서 상태
	private String item_img;	//중고 도서 사진
	private int bk_num;			//도서번호
	private int approval_id;	//등록요청코드
	
	//도서
	private BookVO bookVO;
	//도서 승인
	private BookApprovalVO bookApprovalVO;

	public int getItem_num() {
		return item_num;
	}

	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}

	public int getItem_price() {
		return item_price;
	}

	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}

	public int getItem_grade() {
		return item_grade;
	}

	public void setItem_grade(int item_grade) {
		this.item_grade = item_grade;
	}

	public String getItem_img() {
		return item_img;
	}

	public void setItem_img(String item_img) {
		this.item_img = item_img;
	}

	public int getBk_num() {
		return bk_num;
	}

	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}

	public int getApproval_id() {
		return approval_id;
	}

	public void setApproval_id(int approval_id) {
		this.approval_id = approval_id;
	}

	public BookVO getBookVO() {
		return bookVO;
	}

	public void setBookVO(BookVO bookVO) {
		this.bookVO = bookVO;
	}

	public BookApprovalVO getBookApprovalVO() {
		return bookApprovalVO;
	}

	public void setBookApprovalVO(BookApprovalVO bookApprovalVO) {
		this.bookApprovalVO = bookApprovalVO;
	}
}