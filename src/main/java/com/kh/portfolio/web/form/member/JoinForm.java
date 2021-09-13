package com.kh.portfolio.web.form.member;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class JoinForm {
	
	@NotEmpty(message = "아이디를 입력하세요")
	@Email(message = "")
	private String jid;
	
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
	private String jpw; 	
	@NotEmpty(message="비밀번호 확인을 입력하세요")
	private String jpwChk;
	
	@NotEmpty(message="질문을 입력하세요")
	private String jquestion;	
	@NotEmpty(message="답변을 입력하세요")
	private String janswer;		
	
	@NotEmpty(message="이름을 입력하세요")
	@Pattern(regexp="^[가-힣]*$", message="한글 2~5자리만 가능합니다")
	private String jname;		
	
	@NotNull(message="생년월일을 입력하세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate jbirth;
	
	@NotNull(message="성별을 선택하세요")
	private Gender gender;
	
	@NotEmpty(message="전화번호를 입력하세요")
	private String jtel;
	
	@NotEmpty(message="주소를 입력하세요")
	private String jaddress;
	
	@NotEmpty(message="닉네임을 입력하세요")
	private String jnickname;
}
