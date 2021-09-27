package com.kh.wheregarden.web.form.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {

	private String id;     			//회원 아이디
	private String nickname;		//
	private String role;
}
