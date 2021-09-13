package com.kh.portfolio.domain.member.dao;

import com.kh.portfolio.domain.member.dto.MemberDTO;

public interface MemberDAO {
	
	/**
	 * 회원가입
	 */
	MemberDTO createMember(MemberDTO memberDTO);
	
	/**
	 * 회원존재유무 체크
	 * @param email
	 * @return
	 */
	boolean isExistEmail(String Mid);
	
	/**
	 * 회원정보조회(ById)
	 */
	MemberDTO findMemberById(String mid);
	
	
	/**
	 * 회원정보수정
	 */
	MemberDTO modifyMember(String mid, MemberDTO memberDTO);
	
	/**
	 * 회원탈퇴
	 */
	void deleteMember(String mid);
	
	/**
	 * 로그인 체크
	 * @param id
	 * @param pw
	 * @return
	 */
	boolean isOurMember(String Mid, String Mpw);
}
