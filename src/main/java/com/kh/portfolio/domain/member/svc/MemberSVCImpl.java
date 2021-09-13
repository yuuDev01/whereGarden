package com.kh.portfolio.domain.member.svc;

import org.springframework.stereotype.Service;

import com.kh.portfolio.domain.member.dao.MemberDAO;
import com.kh.portfolio.domain.member.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {

	private final MemberDAO memberDAO;
	/**
	 * 회원가입 DAO꺼 가져옴
	 */
	@Override
	public MemberDTO createMember(MemberDTO memberDTO) {
		
		return memberDAO.createMember(memberDTO);
	}
	
	//이메일 존재유무 확인
	@Override
	public boolean isExistEmail(String mid) {
		
		return memberDAO.isExistEmail(mid);
	}
	
	/**
	 * 회원정보찾기(ById) DAO꺼 가져옴
	 */
	@Override
	public MemberDTO findMemberById(String mid) {
		
		return memberDAO.findMemberById(mid);
	}

	
	/**
	 * 회원 정보 수정 DAO꺼 가져옴
	 */
	@Override
	public MemberDTO modifyMember(String mid, MemberDTO memberDTO) {
		
		return memberDAO.modifyMember(mid, memberDTO);
	}
	
	/**
	 * 회원탈퇴 DAO꺼 가져옴
	 */
	@Override
	public void deleteMember(String mid) {
		
		memberDAO.deleteMember(mid);
	}
	
	/**
	 * 로그인 체크
	 */
	@Override
	public MemberDTO isOurMember(String mid, String mpw) {
		
		MemberDTO ourMemberDTO = null;
		if(memberDAO.isOurMember(mid, mpw)) {
			ourMemberDTO = memberDAO.findMemberById(mid);
		}
		
		return ourMemberDTO;
	}

}
