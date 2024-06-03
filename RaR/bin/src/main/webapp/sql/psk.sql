CREATE TABLE book(
	bk_num	number PRIMARY KEY,
	bk_name	varchar2(30) NOT NULL,
	bk_writer varchar2(60) NOT NULL,
	bk_publisher varchar2(100) NOT NULL,
	bk_price number NOT NULL,
	bk_img	varchar2(500) NOT NULL,
	bk_genre varchar2(60) NOT NULL
);
create sequence book_seq;

CREATE TABLE BOOK_APPROVAL (
	approval_id	Number		NOT NULL,
	status	Number		NOT NULL,
	request_at	DATE	DEFAULT  SYSDATE	NOT NULL,
	approved_at	DATE		NOT NULL,
	item_grade	Number		NOT NULL,
	bk_name	VARCHAR2(300)		NOT NULL,
	ad_comment	CLOB		NULL,
	user_num	number		NOT NULL,
	bk_num	number		NOT NULL,
	constraint approval_fk foreign key (user_num) references member (user_num)
);

ALTER TABLE BOOK_APPROVAL ADD CONSTRAINT PK_BOOK_APPROVAL PRIMARY KEY (
	approval_id
);
create sequence approval_id_seq;
