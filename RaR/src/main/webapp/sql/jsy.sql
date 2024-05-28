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

-----장르게시판 상세내용
create table BOARD_GENRE_USER(
bgu_num number not null,
bgu_content varchar2(900) not null,
bgu_date varchar2(50) not null,
bgu_redate varchar2(50) not null,
bg_num number not null,
constraint board_genre_user_pk primary key(bgu_num),
constraint board_genre_user_fk foreign key(bg_num)references board_genre(bg_num)
);


-----자유게시판 좋아요 관리
create table BOARD_FAV(
user_num number not null,
board_num number not null,
constraint board_fav_fk foreign key(user_num)references member(user_num),
constraint board_fav_fk2 foreign key(board_num)references board(board_num)
);

-----자유게시판
create table BOARD(
board_num number not null,
title varchar2(150) not null,
content clob,
hit number not null,
reg_date date not null,
modify_date date,
filename varchar2(30),
user_ip varchar2(50) not null,
user_num number not null,
constraint board_pk primary key(board_num),
constraint board_fk foreign key(user_num)references member(user_num)
);

-----자유게시판 답글 테이블
create table BOARD_REPLY(
re_num number not null,
user_num number not null,
board_num number not null,
content clob not null,
reg_date date not null,
modify_date date,
user_ip varchar2(50) not null,
constraint board_reply_pk primary key(re_num),
constraint board_reply_fk foreign key(user_num)references member(user_num),
constraint board_reply_fk2 foreign key(board_num)references board(board_num)
);

create SEQUENCE board_seq;
create SEQUENCE board_genre_seq;
create SEQUENCE trade_seq;