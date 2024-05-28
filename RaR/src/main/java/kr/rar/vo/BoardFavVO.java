package kr.rar.vo;

public class BoardFavVO {
private int board_num;
private int user_num;

public BoardFavVO() {}

public BoardFavVO(int board_num, int user_num) {
	super();
	this.board_num = board_num;
	this.user_num = user_num;
}

public int getBoard_num() {
	return board_num;
}

public void setBoard_num(int board_num) {
	this.board_num = board_num;
}

public int getUser_num() {
	return user_num;
}

public void setUser_num(int user_num) {
	this.user_num = user_num;
}


}
