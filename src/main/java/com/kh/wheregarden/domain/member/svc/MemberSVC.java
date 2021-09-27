package com.kh.wheregarden.domain.member.svc;


import java.sql.Date;

import com.kh.wheregarden.domain.member.dto.MemberDTO;

public interface MemberSVC {

	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	void join(MemberDTO memberDTO);
	
	/**
	 * 조회 by email
	 * @param email
	 * @return
	 */
	MemberDTO findByEmail(String Mid);
	
	/**
	 * 회원존재유무 체크
	 * @param email
	 * @return
	 */
	boolean isExistEmail(String Mid);
	
	/**
	 * 로그인 체크
	 * @param id
	 * @param pw
	 * @return
	 */
	MemberDTO isLogin(String Mid, String Mpw);
	
	/**
	 * 회원 유무체크
	 * @param email
	 * @param pw
	 * @return
	 */
	boolean isMeMember(String Mid,String Mpw);
	
	/**
	 * 전화번호 존재유무 체크
	 * @param mtel
	 * @return
	 */
	boolean isExistTell(String mtel);
	
	/**
	 * 탈퇴한 이메일 체크
	 * @param mid
	 */
	boolean deleteEmail(String mid);
	
	/**
	 * 닉네임 존재 유무 체크
	 * @param Mnickname
	 * @return
	 */
	boolean isExistNickname(String Mnickname);
	
	/**
	 * 수정
	 * @param id
	 * @param memberDTO
	 */
	void update(String Mid, MemberDTO memberDTO);
	/**
	 * 이메일 찾기
	 * @param tel
	 * @param birth
	 * @return 이메일
	 */
	
	
	
	String findEmail(String Mname, String Mtel);
	
	/**
	 * 비밀번호 찾기
	 * @param email
	 * @param tel
	 * @param birth
	 * @return
	 */
	String findPw(String mid,String Mquestion,String Manswer);	
	
	
	
	void delete(String mid);
	
	/**
	 * mid로 회원 탈퇴
	 * @param mid
	 */
	void outMember(String mid);
	
	/**
	 * 회원정보조회
	 */
	public MemberDTO findMemberById(String mid);
	
}
