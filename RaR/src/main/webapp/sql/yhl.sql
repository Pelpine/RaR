--RAR_ORDER(주문 정보 테이블)
CREATE TABLE RAR_ORDER (
	order_num	NUMBER		NOT NULL,
	total_amount	NUMBER		NOT NULL,
	order_date	DATE	DEFAULT SYSDATE	NOT NULL,
	order_name	VARCHAR2(200)		NOT NULL,
	zipcode	NUMBER		NOT NULL,
	address1	VARCHAR2(200)		NOT NULL,
	address2	VARCHAR2(200)		NOT NULL,
	order_pay	NUMBER		NOT NULL,
	status	NUMBER	DEFAULT 1	NOT NULL,
	user_num	NUMBER		NOT NULL,
	bk_num	NUMBER		NOT NULL,
	constraint RAR_ORDER_pk primary key (order_num),
	constraint RAR_ORDER_user_fk foreign key (user_num) references MEMBER (user_num),
	constraint RAR_ORDER_book_fk foreign key (bk_num) references BOOK (bk_num)
);
CREATE sequence RAR_ORDER_SEQ;

--ITEM(상품 테이블)
CREATE TABLE ITEM (
	item_num	NUMBER		NOT NULL,
	item_price	NUMBER		NOT NULL,
	item_grade	NUMBER		NOT NULL,
	item_img	VARCHAR2(200)		NOT NULL,
	item_info	CLOB		NOT NULL,
	bk_num	NUMBER		NOT NULL,
	approval_id	NUMBER		NOT NULL,
	constraint ITEM_pk primary key (item_num),
	constraint ITEM_book_fk foreign key (bk_num) references BOOK (bk_num),
	constraint ITEM_approval_fk foreign key (approval_id) references BOOK_APPROVAL (approval_id)
);
CREATE sequence ITEM_SEQ;

--RAR_ORDER_ITEM(주문별 구매 상품)
CREATE TABLE RAR_ORDER_ITEM (
	order_num	NUMBER		NOT NULL,
	item_num	NUMBER		NOT NULL,
	bk_num		NUMBER		NOT NULL,
	constraint RAR_ORDER_ITEM_order_fk foreign key (order_num) references RAR_ORDER (order_num),
	constraint RAR_ORDER_ITEM_item_fk foreign key (item_num) references ITEM (item_num),
	constraint RAR_ORDER_ITEM_bk_fk foreign key (bk_num) references BOOK (bk_num)
);

--CART(장바구니)
CREATE TABLE CART (
	cart_num	NUMBER		NOT NULL,
	user_num	NUMBER		NOT NULL,
	item_num	NUMBER		NOT NULL,
	bk_num		NUMBER		NOT NULL,
	reg_date	DATE	default	sysdate	not null,
	constraint CART_pk primary key (cart_num),
	constraint CART_user_fk foreign key (user_num) references MEMBER(user_num),
	constraint CART_item_fk foreign key (item_num) references ITEM (item_num),
	constraint CART_bk_fk foreign key (bk_num) references BOOK (bk_num)
);
CREATE sequence CART_SEQ;





