CREATE TABLE EVENT_LIST(
    event_num number primary key not null,
    event_name varchar2(300) not null,
    event_date date default sysdate not null,
    event_img varchar2(500) not null,
    event_content varchar2(300) not null,
    event_start date not null,
    event_end date not null,
    user_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num)
);

CREATE SEQUENCE event_seq;

CREATE TABLE EVENT_ATTENDANCE(
    attendance_date date not null,
    event_num number not null,
    user_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (event_num) REFERENCES EVENT_LIST(event_num)
);

CREATE TABLE EVENT_REWARDS(
    reward_num number primary key not null,
    reward_point number not null,
    event_num number not null,
    user_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (event_num) REFERENCES EVENT_LIST(event_num)
);
CREATE SEQUENCE reward_seq;

CREATE TABLE TRADE_BOARD(
    board_num number primary key not null,
    user_num number not null,
    title varchar2(150) not null,
    content clob not null,
    hit number default 0 not null,
    reg_date date default sysdate not null,
    modify_date date,
    filename varchar2,
    user_ip varchar2(100) not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num)
);

CREATE SEQUENCE trade_seq;

CREATE TABLE TRADE_FAV(
    user_num number not null,
    board_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (board_num) REFERENCES TRADE_BOARD(board_num)
);

CREATE TABLE TRADE_REPLY(
    re_num number primary key not null,
    content clob not null,
    reg_date date not null,
    modify_date date,
    user_ip varchar2 not null,
    user_num number not null,
    board_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (board_num) REFERENCES TRADE_BOARD(board_num)
);

CREATE SEQUENCE trreply_seq;