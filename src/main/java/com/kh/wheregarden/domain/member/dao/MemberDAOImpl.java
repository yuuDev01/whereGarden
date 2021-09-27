package com.kh.wheregarden.domain.member.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.member.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

	private final JdbcTemplate jt;
	@Override
	public String insert(MemberDTO memberDTO) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into member values(?,?,?,?,?,?,?,?,?,?,?,?,0) ");
		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[] {"Mid"} );
				pstmt.setString(1, memberDTO.getMid());
				pstmt.setString(2, memberDTO.getMpw());
				pstmt.setString(3, memberDTO.getMquestion());
				pstmt.setString(4, memberDTO.getManswer());
				pstmt.setString(5, memberDTO.getMname());
				pstmt.setDate(6, memberDTO.getMbirth());
				pstmt.setString(7,memberDTO.getMgender());
				pstmt.setString(8, memberDTO.getMtel());
				pstmt.setString(9, memberDTO.getMpost());
				pstmt.setString(10, memberDTO.getMroadname());
				pstmt.setString(11, memberDTO.getMaddress());
				pstmt.setString(12, memberDTO.getMnickname());
				
				return pstmt;
			}
		},keyHolder);

		return keyHolder.getKeyAs(String.class);
	}

//회원조회 by id
	@Override
	public MemberDTO findByID(long Mid) {
		
StringBuffer sql = new StringBuffer();
		
		sql.append("select  ");
		sql.append("    Mid, ");
		sql.append("    Mpw, ");
		sql.append("    MQUESTION,  ");
		sql.append("    MANSWER, ");
		sql.append("    MNAME, ");
		sql.append("    MGENDER, ");
		sql.append("    MBIRTH,  ");
		sql.append("    MTEL, ");
		sql.append("    MPOST,  ");
		sql.append("    MROADNAME,  ");
		sql.append("    MADDRESS,  ");
		sql.append("    MNICKNAME  ");
		sql.append("  from member ");
		sql.append(" where Mid = ?  ");
		
		MemberDTO mdto = jt.queryForObject(
				sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class),Mid);
		return mdto;
	}
	
	//회원조회 by email
	@Override
	public MemberDTO findByEmail(String Mid) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select  ");
		sql.append("    Mid, ");
		sql.append("    Mpw, ");
		sql.append("    MQUESTION,  ");
		sql.append("    MANSWER, ");
		sql.append("    MNAME, ");
		sql.append("    MGENDER, ");
		sql.append("    MBIRTH,  ");
		sql.append("    MTEL, ");
		sql.append("    MPOST, ");
		sql.append("    MROADNAME, ");
		sql.append("    MADDRESS,  ");
		sql.append("    MNICKNAME  ");
		sql.append("  from member ");
		sql.append(" where Mid = ?  ");
		
		MemberDTO mdto = jt.queryForObject(
				sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class),Mid);
		
		return mdto;
	}
	
	//이메일 존재유무
	@Override
	public boolean isExistEmail(String Mid) {
				boolean isExistEmail = false;
		
		String sql = "select count(Mid) from member where MId = ? and mresign=0 ";
		Integer cnt = jt.queryForObject(sql, Integer.class, Mid);
		
		if(cnt == 1) isExistEmail = true;
		
		return isExistEmail;
	}
	
	//전화번호 존재유무
	@Override
	public boolean isExistTell(String mtel) {
		boolean isExistTell = false;
		
		String sql = "select count(Mtel) from member where Mtel = ? and mresign=0 ";
		Integer cnt = jt.queryForObject(sql, Integer.class, mtel);
		
		if(cnt == 1) isExistTell = true;
		
		return isExistTell;
	}
	
	//탈퇴한 이메일
	@Override
	public boolean deleteEmail(String mid) {
		boolean deleteEmail = false;
		
		String sql = "select count(Mid) from member where MId = ? and mresign=1";
		Integer cnt = jt.queryForObject(sql, Integer.class, mid);
		
		if(cnt == 1) deleteEmail = true;
		
		return deleteEmail;
	}
	
	//닉네임 존재 유무
	@Override
	public boolean isExistNickname(String Mnickname) {
		 boolean isExistNickname = false;
		 
		 String sql = "select count(Mnickname) from member where mnickname = ? ";
		 Integer cnt = jt.queryForObject(sql, Integer.class, Mnickname);
		 
		 if(cnt == 1 ) isExistNickname = true;
		 
		return isExistNickname;
	}
	
	//로그인 체크
	@Override
	public boolean isLogin(String Mid, String Mpw) {
		boolean isLogin = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(Mid) ");
		sql.append("  from member ");
		sql.append(" where Mid = ? ");
		sql.append("   and Mpw = ? ");
		sql.append("   and Mresign = 0 ");
		
		Integer cnt = 
				jt.queryForObject(sql.toString(), Integer.class, Mid, Mpw);
		if(cnt == 1) isLogin = true;
		
		return isLogin;
	}
	
	@Override
	public List<MemberDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//회원수정
	@Override
	public void update(String Mid, MemberDTO memberDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("   set mpw = ?, ");
		sql.append("   		 MQUESTION = ?, ");
		sql.append("       MANSWER = ?, ");
		sql.append("       Mtel = ?, ");
		sql.append("       MPOST = ?, ");
		sql.append("       MROADNAME = ?, ");
		sql.append("       MADDRESS = ?, ");
		sql.append("       Mnickname = ? ");
		sql.append("where Mid = ? ");
		
		jt.update(sql.toString(), 
				memberDTO.getMpw(), 
				memberDTO.getMquestion(), 
				memberDTO.getManswer(),
				memberDTO.getMtel(),
				memberDTO.getMpost(),
				memberDTO.getMroadname(),
				memberDTO.getMaddress(),
				memberDTO.getMnickname(),
				Mid);
		
	}

	//회원삭제
	@Override
	public void delete(String Mid) {
		String sql = "delete from member where mid = ? ";
		jt.update(sql, Mid);
		
	}
	
	//회원 탈퇴
	@Override
	public void outMember(String mid) {
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("   set mresign = '1' ");
		sql.append(" where mid = ? ");

		
		jt.update(sql.toString(), mid);
		
	}
	
	//회원아이디 찾기
	@Override
	public String findEmail(String mname, String mtel) {
		StringBuffer sql = new StringBuffer();
		sql.append("select Mid from member ");
		sql.append(" where Mname = ? ");
		sql.append("   and Mtel = ? ");
		
		String email = 
				jt.queryForObject(sql.toString(), String.class, mname, mtel);
		return email;
	}

	//비밀번호 찾기
	@Override
	public String findPw(String mid, String mquestion, String manswer) {
		StringBuffer sql = new StringBuffer();
		sql.append("select mpw from member ");
		sql.append(" where Mid = ? ");
		sql.append("   and mquestion = ? ");
		sql.append("   and Manswer = ? ");
		
		String pw = 
				jt.queryForObject(sql.toString(), String.class, mid, mquestion, manswer);
		return pw;
	}
	
	/**
	 * 회원 상세정보(ById)
	 */
	@Override
	public MemberDTO findMemberById(String mid) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select mid, mpw, mquestion, manswer, mname, mbirth, mgender, mtel, mpost, mroadname, maddress, mnickname, mresign ");
		sql.append("from Member ");
		sql.append("where mid = ? ");
		
		MemberDTO findedMemberDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(MemberDTO.class), mid);
		return findedMemberDTO;
	}


}
