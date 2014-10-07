--DROP TABLE sakdagu_product_image;

CREATE TABLE sakdagu_product_image (
	board_num integer NOT NULL,
	product_id integer ,
  option_id integer ,
	image_name VARCHAR(50) NOT NULL,
	content_type VARCHAR(50) NOT NULL,
	contents BLOB NOT NULL  
);
COMMIT;


select * from SAKDAGU_PRODUCT_IMAGE; 


--DROP TABLE sakdagu_product_option;

CREATE TABLE sakdagu_product_option(
	board_num integer NOT NULL,
	product_id integer ,
	option_id integer ,
	option_title VARCHAR(50),
  price1 integer,
  price2 integer,
  quantity integer,
  installment varchar(12)
  );
COMMIT;
select * from SAKDAGU_PRODUCt_option; 



--DROP TABLE sakdagu_product;

CREATE TABLE sakdagu_product(
	board_num integer NOT NULL,
	product_id integer ,
	product_title VARCHAR(50)
);
COMMIT;
select * from SAKDAGU_PRODUCt; 

--
--SELECT image_name, content_type, contents FROM sakdagu_product_image WHERE target_id=0;
--select sakdagu_board_num_seq.currval from dual;
--select num from sakdagu_board ORDER by num desc;