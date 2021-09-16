package com.kh.wheregarden.web.api;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FindPwReq {

	
	@NotBlank
	@Email
	private String mid;

	@NotBlank
	private String mquestion;

	@NotBlank
	private String manswer;

}
