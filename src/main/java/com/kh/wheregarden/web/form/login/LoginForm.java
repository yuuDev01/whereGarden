package com.kh.wheregarden.web.form.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {

	@NotBlank(message ="이메일을 입력하세요")
	@Email(message ="아이디는 이메일 형식입니다")
	private String mid;
	
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
	private String mpw;
}
