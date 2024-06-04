--회원 정보
CREATE TABLE member (
 user_num number NOT NULL,
 user_name varchar2(12) NOT NULL,
 user_email	varchar2(50) NOT NULL, --아이디/이메일
 user_auth number(1) DEFAULT 2 NOT NULL, --회원 등급:0탈퇴 회원,1:정지 회원,2:일반 회원,9:관리자
 constraint member_pk primary key (user_num)
);
create sequence member_seq;

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
	user_comment clob NULL, --관리자->유저 코멘트
 constraint member_detail_pk primary key (user_num),
 constraint member_detail_fk foreign key (user_num)
                                references member (user_num)
);

--QnA
CREATE TABLE question (
    question_num number NOT NULL,
    question_title varchar2(50) NOT NULL,
    question_content clob NOT NULL,
    question_reg_date DATE DEFAULT SYSDATE NOT NULL,
    user_num number NOT NULL,
 constraint question_pk primary key (question_num),
 constraint question_fk foreign key (user_num),
                           references member (user_num)
);
CREATE sequence question_seq;

CREATE TABLE answer (
    answer_num number NOT NULL,
    answer_content clob NOT NULL,
    answer_reg_date DATE DEFAULT SYSDATE NOT NULL,
    question_num number NOT NULL,
    user_num number NOT NULL,
 constraint answer_pk primary key (answer_num),
 constraint answer_fk foreign key (question_num)
                         references question (question_num),
 constraint answer_fk2 foreign key (user_num)
                         references member (user_num)                     
);