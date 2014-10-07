--------------------------------------------------------
--  파일이 생성됨 - 화요일-10월-07-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table SAKDAGU_ADMIN
--------------------------------------------------------
 --drop TABLE "SKU"."SAKDAGU_CART" ;
  CREATE TABLE "SKU"."SAKDAGU_CART" 
   (	
   	ID VARCHAR2(20 BYTE) not null,
	board_num integer NOT NULL,
	product_id integer ,
	option_id integer 
   );


commit;

select * from SAKDAGU_cart;