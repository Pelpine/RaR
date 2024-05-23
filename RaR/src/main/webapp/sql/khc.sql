CREATE TABLE EVENT_LIST(
	event_num number primary key not null,
	event_name varchar2(300) not null,
	user_num number not null,
	event_date date default sysdate not null,
	event_img varchar2(500),
	event_content varchar2(300) not null,
	event_start date not null,
	event_end date not null,
	constraint event_list_fk foreign key (user_num) 
                                references user (user_num)
);   