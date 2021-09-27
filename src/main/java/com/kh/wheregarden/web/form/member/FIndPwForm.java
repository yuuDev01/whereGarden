package com.kh.wheregarden.web.form.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FIndPwForm {

	@NotBlank
	@Email
	private String mId;
	
	private String mquestion;	
	private String manswer;
}
