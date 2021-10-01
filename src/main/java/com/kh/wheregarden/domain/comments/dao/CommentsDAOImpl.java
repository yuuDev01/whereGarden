package com.kh.wheregarden.domain.comments.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.board.dao.BoardDAOImpl;
import com.kh.wheregarden.domain.board.dto.BoardDTO;
import com.kh.wheregarden.domain.comments.dto.CommentsDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CommentsDAOImpl implements CommentsDAO {
	
	private final JdbcTemplate jt;

	//게시글에 댓글 불러오기
	@Override
	public List<CommentsDTO> showComment(Long cbnum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from comments where cbnum = ? order by ccdate ASC ");
		
		List<CommentsDTO> commentList = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<>(CommentsDTO.class),
				cbnum);
		
		return commentList;
	}
	
	//댓글작성
	@Override
	public Long writeComment(CommentsDTO commentsDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO comments ( ");
		sql.append("  cnum, ");				//댓글 고유 번호
		sql.append("  cbnum, ");			//댓글이 작성된 게시글번호
		sql.append("  cid, ");				//회원 아이디
		sql.append("  cnickname, ");		//회원 닉네임
		sql.append("  ccontent, ");			//댓글 내용
		sql.append("  cgroup) ");			//댓글그룹
		sql.append("	VALUES ( ");
		sql.append("  comments_cnum_seq.nextval, ");
		sql.append("  ?, ");					//댓글이 작성된 게시글번호
		sql.append("  ?, ");					//회원 아이디
		sql.append("  ?, ");					//회원 닉네임
		sql.append("  ?, ");					//댓글 내용
		sql.append("  comments_cnum_seq.currval "); //댓글그룹
		sql.append(") ");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"cnum"});
				
				pstmt.setLong(1, commentsDTO.getCbnum());
				pstmt.setString(2, commentsDTO.getCid());
				pstmt.setString(3, commentsDTO.getCnickname());
				pstmt.setString(4, commentsDTO.getCcontent());
				return pstmt;
			}
		}, keyHolder);
		
		return ((BigDecimal)keyHolder.getKeys().get("cnum")).longValue();
	}
	
	//댓글수정
	@Override
	public String modifyComment(String modifiedContent, Long cnum, String id) {
		log.info(modifiedContent, cnum, id);
		StringBuffer sql = new StringBuffer();
		sql.append("update comments ");
		sql.append("   set ccontent = ?, ");
		sql.append("       cudate = sysdate ");
		sql.append(" where cid = ? and cnum = ? ");
		
		int rows = jt.update(sql.toString(), modifiedContent, id, cnum);
		 
		if(rows != 1) {
			 throw new IllegalArgumentException(cnum + " 번 댓글 번호를 찾을 수 없습니다!");
		}
		
		return modifiedContent;
	}

	//댓글삭제
	@Override
	public void delComment(Long cnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from comments ");
		sql.append(" where cnum = ? ");
		
		if(jt.update(sql.toString(), cnum) != 1) {
			throw new IllegalArgumentException(cnum + " 번 게시글 번호를 찾을 수 없습니다!");
		};
	}
	
	
	//부모 댓글 불러오기
	@Override
	public CommentsDTO findParentComment(Long cnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from comments where cnum = ? ");
		
		CommentsDTO commentsDTO = jt.queryForObject(
				sql.toString(), 
				new BeanPropertyRowMapper<>(CommentsDTO.class),
				cnum
			);
		
		return commentsDTO;
	}

	//대댓글작성
	@Override
	public Long writeReplyComment(CommentsDTO commentsDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO comments ( ");
		sql.append("  cnum, ");				//댓글 고유 번호
		sql.append("  cbnum, ");			//댓글이 작성된 게시글번호
		sql.append("  cid, ");				//회원 아이디
		sql.append("  cnickname, ");		//회원 닉네임
		sql.append("  ccontent, ");			//댓글 내용
		sql.append("  cpnum, ");			//부모댓글 번호
		sql.append("  cgroup) ");			//댓글그룹
		sql.append("	VALUES ( ");
		sql.append("  comments_cnum_seq.nextval, ");
		sql.append("  ?, ");				//댓글이 작성된 게시글번호
		sql.append("  ?, ");				//회원 아이디
		sql.append("  ?, ");				//회원 닉네임
		sql.append("  ?, ");				//댓글 내용
		sql.append("  ?, ");				//부모댓글 번호
		sql.append("  ?, ");				//댓글그룹
		sql.append(") ");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"cnum"});
				
				pstmt.setLong(1, commentsDTO.getCbnum());
				pstmt.setString(2, commentsDTO.getCid());
				pstmt.setString(3, commentsDTO.getCnickname());
				pstmt.setString(4, commentsDTO.getCcontent());
				pstmt.setLong(5, commentsDTO.getCpnum());
				pstmt.setLong(6, commentsDTO.getCgroup());
					
				return pstmt;
			}
		}, keyHolder);
		
		return ((BigDecimal)keyHolder.getKeys().get("cnum")).longValue();
	}


}
