package com.kh.portfolio.domain.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.domain.member.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {
	
	private final JdbcTemplate jt;

	/**
	 * 회원가입
	 */
	@Override
	public MemberDTO createMember(MemberDTO memberDTO) {
			
		StringBuffer sql = new StringBuffer();
		sql.append("insert into member values(?,?,?,?,?,?,?,?,?,?,?) ");
		
		jt.update(sql.toString(), 
				memberDTO.getMid() ,memberDTO.getMpw(), 
				memberDTO.getMquestion(),memberDTO.getManswer(),
				memberDTO.getMname(), memberDTO.getMbirth(),
				memberDTO.getMgender(), memberDTO.getMtel(),
				memberDTO.getMaddress(), memberDTO.getMnickname(), 0);
		
		return memberDTO;
	}

	//이메일 존재유무
	@Override
	public boolean isExistEmail(String Mid) {
		boolean isExistEmail = false;
		
		String sql = "select count(Mid) from member where Mid = ? ";
		Integer cnt = jt.queryForObject(sql, Integer.class, Mid);
		
		if(cnt == 1) isExistEmail = true;
		
		return isExistEmail;
	}
	
	/**
	 * 회원 상세정보(ById)
	 */
	@Override
	public MemberDTO findMemberById(String mid) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select mid, mpw, mquestion, manswer, mname, mbirth, mgender, mtel, maddress, mnickname, mresign ");
		sql.append("from Member ");
		sql.append("where mid = ? ");
		
		MemberDTO findedMemberDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class), mid);
		return findedMemberDTO;
	}
	
	/**
	 * 회원 정보수정
	 */
	@Override
	public MemberDTO modifyMember(String mid, MemberDTO memberDTO) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("set Mpw = ?, ");
		sql.append("	Mquestion = ?, ");
		sql.append("	Manswer = ?, ");
		sql.append("	Mname = ?, ");
		sql.append("	Mtel = ?, ");
		sql.append("	Maddress = ?, ");
		sql.append("	Mnickname = ? ");
		sql.append("where mid = ? ");
		
		jt.update(sql.toString(),
							memberDTO.getMpw(), memberDTO.getMquestion(),
							memberDTO.getManswer(), memberDTO.getMname(),
							memberDTO.getMtel(), memberDTO.getMaddress(),
							memberDTO.getMnickname(), mid);
		
		return findMemberById(mid);
	}


	/**
	 * 회원탈퇴
	 */
	@Override
	public void deleteMember(String mid) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from member ");
		sql.append("where mid = ? ");
		
		jt.update(sql.toString(), mid);
		
	}
	
	//로그인 체크
	@Override
	public boolean isOurMember(String mid, String mpw) {
		boolean isOurMember = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(mid) ");
		sql.append("  from member ");
		sql.append(" where mid = ? ");
		sql.append("   and mpw = ? ");
		
		Integer cnt = 
				jt.queryForObject(sql.toString(), Integer.class, mid, mpw);
		if(cnt == 1) isOurMember = true;
		
		return isOurMember;
	}






	

}
