package com.kh.wheregarden.web.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
public class FindEmailReq {

	@NotBlank
	private String name;
	
	
	@NotBlank
	@Size(max = 13)
	private String tel;
	
	




}
