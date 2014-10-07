--------------------------------------------------------
--  ������ ������ - ȭ����-10��-07-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table SAKDAGU_BOARD
--------------------------------------------------------

  CREATE TABLE "SKU"."SAKDAGU_BOARD" 
   (	"NUM" integer not null, 
	"WRITER" VARCHAR2(60 BYTE) not null, 
	"TITLE" VARCHAR2(300 BYTE) not null, 
	"CONTENTS" CLOB  not null, 
	"IP" VARCHAR2(39 BYTE) not null, 
	"READ_COUNT" integer DEFAULT 0, 
	"REG_DATE" DATE not null, 
	"MOD_DATE" DATE, 
	"CATEGORY" VARCHAR2(24 BYTE) not null, 
	"SUB_CATEGORY" VARCHAR2(24 BYTE) not null)  ;

   COMMENT ON COLUMN "SKU"."SAKDAGU_BOARD"."CATEGORY" IS 'ū ī�׸�������';
   COMMENT ON COLUMN "SKU"."SAKDAGU_BOARD"."SUB_CATEGORY" IS '���� ī�װ���';
   commit;
   
--------------------------------------------------------
--  Constraints for Table SAKDAGU_BOARD
--------------------------------------------------------
