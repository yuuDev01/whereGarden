package com.kh.wheregarden.domain.member.svc;

import javax.transaction.Transactional;



import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.member.dao.MemberDAO;
import com.kh.wheregarden.domain.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class MemberSVCImpl implements MemberSVC {

	private final MemberDAO memberDAO;
	//가입
	@Override
 
	public void join(MemberDTO memberDTO) {
		String id = memberDAO.insert(memberDTO);
		
		
	}
	
	// 이메일 중복체크
	@Override
	public boolean isExistEmail(String Mid) {
		
		return memberDAO.isExistEmail(Mid);
	}

	//닉네임 중복체크
	@Override
	public boolean isExistNickname(String Mnickname) {
		
		return memberDAO.isExistNickname(Mnickname);
	}
	//로그인 체크
	@Override
	public MemberDTO isLogin(String Mid, String Mpw) {
		MemberDTO mdto = null;
		if(memberDAO.isLogin(Mid, Mpw)) {
			mdto = memberDAO.findByEmail(Mid);
		}
		return mdto;
	}
	
//회원 유무 체크
	@Override
	public boolean isMeMember(String Mid, String Mpw) {
		return memberDAO.isLogin(Mid, Mpw);
	}
	
	//전화번호 존재유무
	@Override
	public boolean isExistTell(String mtel) {

		return memberDAO.isExistTell(mtel);
	}
	
	//탈퇴한 회원 체크
	@Override
	public boolean deleteEmail(String mid) {
		return memberDAO.deleteEmail(mid);
	}
	
	
	@Override
	public MemberDTO findByEmail(String Mid) {

		MemberDTO memberDTO = memberDAO.findByEmail(Mid);
		
		return memberDTO;
	}


	
	//회원 수정
	@Override
	public void update(String Mid, MemberDTO memberDTO) {
		//회원수정
		memberDAO.update(Mid, memberDTO);
		
	}

	//아이디 찾기
	@Override
	public String findEmail(String Mname, String Mtel) {
		
		return memberDAO.findEmail(Mname, Mtel);
	}

	//비밀번호 찾기
	@Override
	public String findPw(String mid, String Mquestion, String Manswer) {
		
		return memberDAO.findPw(mid, Mquestion, Manswer);
	}

	//회원삭제
	@Override
	public void delete(String mid) {
		memberDAO.delete(mid);
		
	}

	//회원 탈퇴
	@Override
	public void outMember(String mid) {
		memberDAO.outMember(mid);
		
	}

	/**
	 * 회원정보찾기(ById) DAO꺼 가져옴
	 */
	@Override
	public MemberDTO findMemberById(String mid) {
		
		return memberDAO.findMemberById(mid);
	}
}
