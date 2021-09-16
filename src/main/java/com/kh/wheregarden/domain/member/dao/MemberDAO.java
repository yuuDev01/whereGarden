package com.kh.wheregarden.domain.member.dao;

import java.sql.Date;
import java.util.List;

import com.kh.wheregarden.domain.member.dto.MemberDTO;

public interface MemberDAO {

	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	String insert(MemberDTO memberDTO);

	
	/**
	 * 조회 by id
	 * @param id
	 * @return
	 */
	MemberDTO findByID(long Mid);
	
	/**
	 * 조회 by email
	 * @param email
	 * @return
	 */
	MemberDTO findByEmail(String Mid);
	
	/**
	 * 회원존재유무 체크
	 * @param mid
	 * @return
	 */
	boolean isExistEmail(String Mid);
	
	/**
	 * 탈퇴한 이메일 체크
	 * @param mid
	 */
	boolean deleteEmail(String mid);
	
	/**
	 * 전화번호 존재유무 체크
	 * @param mtel
	 * @return
	 */
	boolean isExistTell(String mtel);
	
	/**
	 * 닉네임 존재 유무 체크
	 * @param Mnickname
	 * @return
	 */
	boolean isExistNickname(String Mnickname);
	/**
	 * 로그인 체크
	 * @param id
	 * @param pw
	 * @return
	 */
	boolean isLogin(String Mid, String Mpw);
	

	
	List<MemberDTO> selectAll();
	
	/**
	 * 수정
	 * @param id
	 * @param memberDTO
	 */
	void update(String Mid, MemberDTO memberDTO);
	
	/**
	 * 삭제
	 * @param id
	 */
	void delete(String Mid);

	/**
	 * 탈퇴
	 * @param mid
	 */
	void outMember(String mid);
	
	
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
	String findPw(String mid,String mquestion,String manswer);
	
	/**
	 * 회원정보조회(ById)
	 */
	MemberDTO findMemberById(String mid);
}
