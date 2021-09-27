package com.kh.wheregarden.domain.member.dto;

import java.sql.Date;

import com.kh.wheregarden.web.form.Gender;

import lombok.Data;

@Data
public class MemberDTO {

	private String mid;			 
	private String mpw; 	
	private String mquestion;	
	private String manswer;		
	private String mname;		
	private Date mbirth;	
	private String mgender;
	private String mtel;
	private String mpost;
	private String mroadname;
	private String maddress;	
	private String mnickname;	
	private String mresign;
}
