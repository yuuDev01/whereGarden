package com.kh.portfolio.domain.member.svc;

import com.kh.portfolio.domain.member.dto.MemberDTO;

public interface MemberSVC {
	
	/**
	 * 회원가입
	 * @param memberDTO
	 * @return
	 */
	public MemberDTO createMember(MemberDTO memberDTO);
	
	/**
	 * 회원존재유무 체크
	 * @param email
	 * @return
	 */
	boolean isExistEmail(String Mid);
	
	/**
	 * 회원정보조회
	 */
	public MemberDTO findMemberById(String mid);
	
	/**
	 * 회원정보수정
	 */
	public MemberDTO modifyMember(String mid, MemberDTO memberDTO);
	
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
	MemberDTO isOurMember(String Mid, String Mpw);
}
