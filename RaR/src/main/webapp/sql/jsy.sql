-----자유 거래 게시판
CREATE TABLE TRADE_BOARD (
	board_num	NUMBER		NOT NULL,
	user_num	NUMBER		NOT NULL,
	title	VARCHAR2		NOT NULL,
	content	CLOB		NOT NULL,
	hit	NUMBER		NOT NULL,
	reg_date	DATE	DEFAULT SYSDATE	NOT NULL,
	modify_date	DATE		NULL,
	filename	VARCHAR2		NULL,
	user_ip	VARCHAR2		NOT NULL,
	img	VARCHAR2		NOT NULL
	constraint trade_board_pk primary key(board_num),
	constraint trade_board_fk foreign key(user_num) references member(user_num)
);


-----자유 거래 게시판 좋아요
create table TRADE_FAV(
	user_num number not null,
	board_num number not null,
	constraint trade_fav_fk foreign key(user_num) references member(user_num),
	constraint trade_fav_fk2 foreign key(board_num) references board(board_num)
);

-----자유 거래 게시판 답글 테이블
create table TRADE_REPLY(
re_num number not null,
board_num number not null,
user_num number not null,
content clob not null,
reg_date date not null,
modify_date date,
user_ip varchar(100) not null,
constraint trade_reply_pk primary key(re_num),
constraint trade_reply_fk foreign key(board_num)references board(board_num),
constraint trade_reply_fk2 foreign key(user_num)references member(user_num)
);

-----장르별 게시판
CREATE TABLE BOARD_GENRE (
	bg_num	number		NOT NULL,
	bg_title	varchar2(100)		NOT NULL,
	user_num	number		NOT NULL,
constraint board_genre_pk primary key(bg_num),
constraint board_genre_fk foreign key(user_num)references member(user_num)
);


