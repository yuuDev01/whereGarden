package com.kh.wheregarden.domain.member.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.wheregarden.domain.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberDAOImplTest {
	
	@Autowired
	private  MemberDAO mdao;
	
	@Test
	@DisplayName("이메일 찾기")
	void findID() {
		MemberDTO mdto = mdao.findByEmail("test@test.com");
		String findedEmail = 
				mdao.findEmail(mdto.getMname(), mdto.getMtel());
		Assertions.assertThat(findedEmail).isEqualTo(mdto.getMid());
	}


}
