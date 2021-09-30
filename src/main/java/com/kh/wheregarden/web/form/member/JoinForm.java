package com.kh.wheregarden.web.form.member;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class JoinForm {

	@NotEmpty(message = "아이디를 입력하세요")
	@Email(message = "")
	private String mid;

	
	//삭제된회원
	private String delmid;
	
	@NotEmpty(message = "비밀번호를 입력하세요")
	@Pattern(regexp = "^.*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message="숫자, 문자, 특수문자 포함 8~15자리 이내")
	private String mpw;							
	
	@NotEmpty(message="비밀번호 확인을 입력하세요")
	private String mpwchk;
	
	@NotEmpty(message="질문을 입력하세요")
	private String mquestion;							
	
	@NotEmpty(message="답변을 입력하세요")
	private String manswer;			
	
	@NotEmpty(message="이름을 입력하세요")
	@Pattern(regexp="^[가-힣]*$", message="한글 2~5자리만 가능합니다")
	private String mname;		
	

	private Date mbirth;							

	@NotNull(message="성별을 선택하세요")
	private String gender;

	@NotEmpty(message="전화번호를 입력하세요")
	@Pattern(regexp="^[0-9]*$", message="숫자만 입력해주세요")
	private String mtel;

	private String mpost;
	
	private String mroadname;
	
	@NotEmpty(message="주소를 입력하세요")
	private String maddress;

	@NotEmpty(message="닉네임을 입력하세요")
	private String mnickname;
	

}
