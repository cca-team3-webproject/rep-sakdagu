delete SAKDAGU_BOARd where SAKDAGU_BOARd.num not in (
select DISTINCT sakdagu_product.board_num from sakdagu_product, sakdagu_product_option where sakdagu_product.board_num=sakdagu_product_option.board_num 
);
commit;
ORDER BY num;

select * from sakdagu_product_option;
select * from sakdagu_product_image;
select * from sakdagu_board;
SELECT * FROM sakdagu_PRODUCT;

  insert INTO sakdagu_PRODUCT values(61,1,'물광2잼');
commit;
select * from sakdagu_product_option  oo;
select board_num as num, min(price2) as min_price from sakdagu_product_option group by BOARD_NUM;
select *
from
(
select board_num as num, min(price2) as min_price from sakdagu_product_option group by  BOARD_NUM );



select *
from
(
select board_num as num, min(price2) as min_price from sakdagu_product_option group by  BOARD_NUM ) opt 
,(SELECT num, writer, title, read_count, reg_date, category, sub_category 
FROM sakdagu_board board WHERE category = '여성의류') bor
where opt.num=bor.num
;

(select board_num as num, min(price2) as min_price from sakdagu_product_option group by BOARD_NUM) opt;

SELECT num, writer, title, read_count, reg_date, category, sub_category, min_price FROM sakdagu_board board,
(select board_num as num, min(price2) as min_price from sakdagu_product_option group by BOARD_NUM) opt WHERE opt.num = board.num and category = '여성의류';

 
 
SELECT  rownum AS r , num, writer, title, read_count, reg_date, category, sub_category,min_price FROM 
(SELECT num, writer, title, read_count, reg_date, category, sub_category FROM sakdagu_board bor) , 
(select board_num, min(price2) as min_price from sakdagu_product_option opt group by BOARD_NUM ) where num=board_num;

SELECT * FROM (
select  rownum AS r , num, writer, title, read_count, reg_date, category, sub_category,min_price from (
SELECT  num, writer, title, read_count, reg_date, category, sub_category,min_price FROM 
  (SELECT num, writer, title, read_count, reg_date, category, sub_category FROM sakdagu_board bor) , 
  (select board_num, min(price2) as min_price from sakdagu_product_option opt group by BOARD_NUM ) where num=board_num
  ORDER BY num DESC
  )
)  WHERE r BETWEEN 1 AND 6;