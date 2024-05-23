CREATE TABLE member (
 user_num number NOT NULL,
 user_name varchar2(12) NOT NULL,
 user_email	varchar2(50) NOT NULL, --아이디/이메일
 user_auth number(1) DEFAULT 2 NOT NULL, --회원 등급:0탈퇴 회원,1:정지 회원,2:일반 회원,9:관리자
 constraint member_pk primary key (user_num)
);

CREATE TABLE member_detail (
	user_num number NOT NULL,
	password varchar2(12) NOT NULL,
	user_phone varchar2(15) NOT NULL,
	user_zipcode varchar2(5) NOT NULL,
	user_address1 varchar2(90) NOT NULL,
	user_address2 varchar2(90) NOT NULL,
	user_photo varchar2(400) NULL,
	user_date date DEFAULT SYSDATE NOT NULL,
	user_ip	varchar2 (40) NOT NULL,
	user_point varchar2(10) DEFAULT 0 NOT NULL,
	user_comment clob NULL,
 constraint member_detail_pk primary key (user_num),
 constraint member_detail_fk foreign key (user_num)
                                references member (user_num)
);

create sequence member_seq;