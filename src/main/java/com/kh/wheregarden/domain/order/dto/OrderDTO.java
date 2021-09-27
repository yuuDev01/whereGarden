package com.kh.wheregarden.domain.order.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

	private List<OrderDetailDTO> orderDetails;
	
	private String onum;		//	ONUM	VARCHAR2(50 BYTE)
	private String omid;		//	OMID	VARCHAR2(40 BYTE)
	private Date odate;			//	ODATE	TIMESTAMP(6)
	private String oname;		//	ONAME	VARCHAR2(10 BYTE)
	private String opost;		//	OPOST	VARCHAR2(10 BYTE)
	private String oroadname;
	private String oaddress;		//	OADDRESS	VARCHAR2(50 BYTE)
	private String ophone;		//	OPHONE	VARCHAR2(20 BYTE)
	private String omemo;		//	OMEMO	CLOB
	private String opayment;		//	OPAYMENT	VARCHAR2(10 BYTE)
	private String obank;		//	OBANK	VARCHAR2(10 BYTE)
	private String ocardcom;		//	OCARDCOM	VARCHAR2(10 BYTE)
	private String ocardnum;		//	OCARDNUM	VARCHAR2(20 BYTE)
	private int oprice;			//	OPRICE	NUMBER(10,0)
	private int oresign;
	private Date rdate; //취소된 날짜
}
