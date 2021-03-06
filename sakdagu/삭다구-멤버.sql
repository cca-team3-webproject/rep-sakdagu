--drop TABLE sakdagu_MEMBER ;

CREATE TABLE sakdagu_MEMBER 
(
  MEMBERID VARCHAR2(15 BYTE) NOT NULL 
, PASSWORD VARCHAR2(10 BYTE) NOT NULL 
, NAME VARCHAR2(20 BYTE) NOT NULL 
, EMAIL VARCHAR2(60 BYTE) 
, TEL VARCHAR2(20 BYTE) 
, ZIPCODE1 VARCHAR2(3 BYTE)
, ZIPCODE2 VARCHAR2(3 BYTE) 
, ADDRESS VARCHAR2(256 BYTE) 
, POINT int 
, MEMBERDATE DATE NOT NULL 
);
commit;

select * from sakdagu_member;
