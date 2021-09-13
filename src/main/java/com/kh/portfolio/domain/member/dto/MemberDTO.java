package com.kh.portfolio.domain.member.dto;

import lombok.Data;

import java.time.LocalDate;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.kh.portfolio.web.form.member.Gender;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	private String mid;					// mid        varchar2(30) PRIMARY key,
	private String mpw; 				// mpw       	varchar2(30),
	private String mquestion;		// mquestion  varchar2(30),
	private String manswer;			// manswer  	varchar2(30),
	private String mname;				// mname  		varchar2(20),
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate mbirth; 	// mbirth      date         not null,
	private String mgender;			// mgender    varchar2(10),
	private String mtel; 				// mtel    		varchar2(15),
	private String maddress;		// maddress   varchar2(50),
	private String mnickname;		// mnickname  varchar2(30),
	private int mresign;				// mresign		number(1)
}
