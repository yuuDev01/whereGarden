package com.kh.portfolio.web.form.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class LoginForm {

	@NotEmpty(message = "")
	@Email(message="이메일 형식이 아닙니다.")
	private String lid;
	
	@NotEmpty(message = "")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$", message="영어소문자,숫자,특수문자를 포함한 8자 이상입니다.")
	private String lpw;
}