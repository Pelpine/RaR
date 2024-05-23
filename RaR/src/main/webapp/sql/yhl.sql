--장바구니(CART)
CREATE TABLE "CART" (
	"order_item"	NUMBER		NOT NULL,
	"user_num"	NUMBER		NOT NULL,
	"item_num"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "CART"."user_num" IS '시퀀스를 이용해 값 지정';

ALTER TABLE "CART" ADD CONSTRAINT "PK_CART" PRIMARY KEY (
	"order_item"
);


--ITEM(상품 테이블)
CREATE TABLE "item" (
	"item_num"	NUMBER		NOT NULL,
	"item_price"	NUMBER		NOT NULL,
	"item_grade"	NUMBER		NOT NULL,
	"item_img"	VARCHAR2(200)		NOT NULL,
	"item_inf"	CLOB		NOT NULL,
	"bk_num"	NUMBER		NOT NULL,
	"approval_id"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "item"."item_grade" IS '도서 상태(1:매우 양호2:양호3:불량)';

ALTER TABLE "item" ADD CONSTRAINT "PK_ITEM" PRIMARY KEY (
	"item_num"
);


--ORDER_ITEM
CREATE TABLE "ORDER_ITEM" (
	"order_num"	NUMBER		NOT NULL,
	"item_num"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ORDER_ITEM"."order_num" IS '주문내역 고유번호, sequence 생성';

ALTER TABLE "CART" ADD CONSTRAINT "PK_CART" PRIMARY KEY (
	"order_item"
);

--ORDER
CREATE TABLE "ORDER" (
	"order_num"	NUMBER		NOT NULL,
	"total_amount"	NUMBER		NOT NULL,
	"order_date"	DATE	DEFAULT SYSDATE	NOT NULL,
	"order_name"	VARCHAR2(200)		NOT NULL,
	"zipcode"	NUMBER		NOT NULL,
	"address1"	VARCHAR2(200)		NOT NULL,
	"address2"	VARCHAR2(200)		NOT NULL,
	"order_pay"	NUMBER		NOT NULL,
	"status"	NUMBER	DEFAULT 1	NOT NULL,
	"user_num"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ORDER"."order_num" IS '주문내역 고유번호, sequence 생성';

COMMENT ON COLUMN "ORDER"."user_num" IS '시퀀스를 이용해 값 지정';

ALTER TABLE "ORDER" ADD CONSTRAINT "PK_ORDER" PRIMARY KEY (
	"order_num"
);