package com.kh.wheregarden.web.form.board;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class WriteForm {
	
	@NotBlank
	private String bmid;
	@NotBlank
	private String bnickname;
	@NotBlank
	private String bcategory;
	@NotBlank
	private String btitle;
	@NotBlank
	private String bcontent;
	
	private List<MultipartFile> files; //파일첨부
}
