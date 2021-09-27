package com.kh.wheregarden.web.form.member;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.kh.wheregarden.web.form.Gender;

import lombok.Data;

@Data
public class EditForm {

	private String mid;						
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
	private String mpw;							
	@NotEmpty(message = "비밀번호 확인을 입력하세요")
	@Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
	private String mpwchk;
	@NotEmpty(message = "비밀번호 찾기 질문을 선택하세요")
	private String mquestion;							
	@NotEmpty(message = "비밀번호 찾기 답변을 입력하세요")
	private String manswer;			
	private String mname;					
	private Date mbirth;							
	private String mgender;
	@NotEmpty(message = "전화번호를 입력하세요")
	private String mtel;
	private String mpost;
	private String mroadname;
	private String maddress;
	@NotEmpty(message = "닉네임을 입력하세요")
	private String mnickname;
}
