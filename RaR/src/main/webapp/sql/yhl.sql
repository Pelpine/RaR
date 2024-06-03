--RAR_ORDER(주문 정보 테이블)
CREATE TABLE RAR_ORDER (
	order_num	NUMBER		NOT NULL,
	order_date	DATE	DEFAULT SYSDATE	NOT NULL,
	order_status	NUMBER	DEFAULT 1	NOT NULL, --배송 상태,1:배송대기,2:배송준비,3:배송중,4:배송완료,5:주문취소
	order_points	NUMBER	NOT NULL, --적립 포인트
	pay_total	NUMBER		NOT NULL, --총 주문금액
	pay_points	NUMBER DEFAULT 0	NOT NULL, -- 사용된 포인트, 기본값 0
	pay_ship	NUMBER 		NOT NULL, --배송비	
	pay_payment	NUMBER		NOT NULL, --결제방식,1:계좌입금,2:신용카드
	receive_name	VARCHAR2(30)		NOT NULL,
	receive_post	NUMBER(5)		NOT NULL,
	receive_address1	VARCHAR2(90)		NOT NULL,
	receive_address2	VARCHAR2(90)		NOT NULL,
	receive_phone	VARCHAR2(15)		NOT NULL,
	notice		VARCHAR2(4000),
	user_num	NUMBER		NOT NULL,
	constraint RAR_ORDER_pk primary key (order_num),
	constraint RAR_ORDER_user_fk foreign key (user_num) references MEMBER (user_num)
);
CREATE sequence RAR_ORDER_SEQ;

--RAR_ORDER_DETAIL(주문별 구매 상품)
CREATE TABLE RAR_ORDER_DETAIL (
	detail_num	NUMBER		NOT NULL,
	item_num	NUMBER		NOT NULL,
	item_name	VARCHAR2(1000)	NOT NULL,
	bk_img		VARCHAR2(500)	NOT NULL,
	bk_price	NUMBER		NOT NULL,--정가
	item_price	NUMBER		NOT NULL,--판매가
	item_grade	NUMBER(1)	NOT NULL,
	order_num	NUMBER		NOT NULL,
	constraint RAR_ORDER_DETAIL_pk primary key (detail_num),
	constraint RAR_ORDER_DETAIL_order_fk foreign key (order_num) references RAR_ORDER (order_num)
);
CREATE sequence RAR_ORDER_DETAIL_SEQ;

--ITEM(상품 테이블)
CREATE TABLE ITEM (
	item_num	NUMBER		NOT NULL,
	item_price	NUMBER		NOT NULL,
	item_grade	NUMBER		NOT NULL,
	item_img	VARCHAR2(200)		NOT NULL,
	item_status	NUMBER	default 1	NOT NULL,
	bk_num	NUMBER		NOT NULL,
	approval_id	NUMBER		NOT NULL,
	item_status NUMBER		NOT NULL, --상품 상태,1:판매중,2:판매완료,3:판매정지
	constraint ITEM_pk primary key (item_num),
	constraint ITEM_book_fk foreign key (bk_num) references BOOK (bk_num),
	constraint ITEM_approval_fk foreign key (approval_id) references BOOK_APPROVAL (approval_id)
);
CREATE sequence ITEM_SEQ;

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





