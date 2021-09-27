package com.kh.wheregarden.web.form.board;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ModifyForm {
	
	private Long 	 bnum;				//글 번호
	private Long 	 bpnum;				//부모글 번호
	
	@NotBlank
	private String bcategory;
	@NotBlank
	private String bmid;
	@NotBlank
	private String bnickname;
	@NotBlank
	private String btitle;
	@NotBlank
	private String bcontent;
	
	private List<MultipartFile> files; //파일첨부	
}
