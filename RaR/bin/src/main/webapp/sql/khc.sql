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

CREATE TABLE TRADE_BOARD (
    board_num NUMBER PRIMARY KEY NOT NULL,
    user_num NUMBER NOT NULL,
    title VARCHAR2(150) NOT NULL,
    content CLOB NOT NULL,
    hit NUMBER DEFAULT 0 NOT NULL,
    reg_date DATE DEFAULT SYSDATE NOT NULL,
    modify_date DATE,
    filename VARCHAR2(255), -- 길이를 설정함
    user_ip VARCHAR2(100) NOT NULL,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num)
);

CREATE SEQUENCE trade_seq;

CREATE TABLE TRADE_FAV(
    user_num number not null,
    board_num number not null,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (board_num) REFERENCES TRADE_BOARD(board_num)
);

CREATE TABLE TRADE_REPLY (
    re_num NUMBER PRIMARY KEY NOT NULL,
    content CLOB NOT NULL,
    reg_date DATE NOT NULL,
    modify_date DATE,
    user_ip VARCHAR2(100) NOT NULL, -- 길이를 설정함
    user_num NUMBER NOT NULL,
    board_num NUMBER NOT NULL,
    FOREIGN KEY (user_num) REFERENCES MEMBER(user_num),
    FOREIGN KEY (board_num) REFERENCES TRADE_BOARD(board_num)
);


CREATE SEQUENCE trreply_seq;