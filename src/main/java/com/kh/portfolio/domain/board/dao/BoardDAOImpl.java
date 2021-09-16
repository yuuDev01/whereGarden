package com.kh.portfolio.domain.board.dao;

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

import com.kh.portfolio.domain.board.dto.BoardDTO;
import com.kh.portfolio.domain.board.dto.SearchDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {

	private final JdbcTemplate jt;
	
	//게시글 작성
	@Override
	public Long boardWrite(BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO board ( ");
		sql.append("  bnum, ");				//게시판 고유번호
		sql.append("  bmid, ");				//회원아이디
		sql.append("  bnickname, ");	//회원닉네임
		sql.append("  bcategory, ");	//게시판 카테고리
		sql.append("  btitle, ");			//게시판 제목
		sql.append("  bcontent, ");		//게시판 내용
		sql.append("  bhit, ");				//조회수
		sql.append("  bgroup, ");			//답글그룹
		sql.append("  bstep, ");			//답글순서
		sql.append("  bindent) ");		//답글들여쓰기
		sql.append("	VALUES ( ");
		sql.append("  board_bnum_seq.nextval, ");
		sql.append("  ?, ");					//회원아이디
		sql.append("  ?, ");					//회원닉네임
		sql.append("  ?, ");					//게시판 카테고리
		sql.append("  ?, ");					//게시판 제목
		sql.append("  ?, ");					//게시판 내용
		sql.append("  0, ");					//조회수
		sql.append("  BOARD_BNUM_SEQ.currval, "); //답글그룹
		sql.append("  0, ");					//답글순서
		sql.append("  0 ");						//답글들여쓰기
		sql.append(") ");

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"bnum"});
				
				pstmt.setString(1, boardDTO.getBmid());
				pstmt.setString(2, boardDTO.getBnickname());
				pstmt.setString(3, boardDTO.getBcategory());
				pstmt.setString(4, boardDTO.getBtitle());
				pstmt.setString(5, boardDTO.getBcontent());
					
				return pstmt;
			}
		}, keyHolder);
		
		return ((BigDecimal)keyHolder.getKeys().get("bnum")).longValue();
	}

	//답글 작성
	@Override
	public Long replyWrite(BoardDTO boardDTO) {
		
		//부모글의 bgroup중 bstep이 부모글의 bstep보다 큰 게시글 bstep + 1
		updateStep(boardDTO.getBgroup(), boardDTO.getBstep());
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO board ( ");
		sql.append("  bnum, ");
		sql.append("  bmid, ");
		sql.append("  bnickname, ");
		sql.append("  bcategory, ");
		sql.append("  btitle, ");
		sql.append("  bcontent, ");
		sql.append("  bhit, ");
		sql.append("  bpnum, ");
		sql.append("  bgroup, ");
		sql.append("  bstep, ");
		sql.append("  bindent) ");
		sql.append("	VALUES ( ");
		sql.append("  board_bnum_seq.nextval, ");
		sql.append("  ?, ");	//회원아이디
		sql.append("  ?, ");	//회원닉네임
		sql.append("  ?, ");	//게시판 카테고리
		sql.append("  ?, ");	//게시판 제목
		sql.append("  ?, ");	//게시판 내용
		sql.append("  0, ");	//조회수
		sql.append("  ?, ");	//부모글
		sql.append("  ?, ");	//답글그룹
		sql.append("  ?, ");	//답글순서
		sql.append("  ? "); 	//답글들여쓰기
		sql.append(") ");

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"bnum"});
				
				pstmt.setString(1, boardDTO.getBmid());
				pstmt.setString(2, boardDTO.getBnickname());
				pstmt.setString(3, boardDTO.getBcategory());
				pstmt.setString(4, boardDTO.getBtitle());
				pstmt.setString(5, boardDTO.getBcontent());
				pstmt.setLong(6, boardDTO.getBpnum());
				pstmt.setLong(7, boardDTO.getBgroup());
				pstmt.setLong(8, boardDTO.getBstep() + 1);
				pstmt.setLong(9, boardDTO.getBindent() + 1);
					
				return pstmt;
			}
		}, keyHolder);
		
		return ((BigDecimal)keyHolder.getKeys().get("bnum")).longValue();
	}

	//답글 생성시 스텝 상승
	private void updateStep(Long bgroup, Long bstep) {
		StringBuilder sql = new StringBuilder();
		sql.append("update board ");
		sql.append("   set bstep = bstep + 1 ");
		sql.append(" where bgroup = ? ");
		sql.append("   and bstep > ? ");
		
		jt.update(sql.toString(), bgroup,bstep);
	}
	
	//게시글 수정
	@Override
	public Long boardModify(Long bnum, BoardDTO boardDTO) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("   set bcategory = ?, ");
		sql.append("       btitle = ? , ");
		sql.append("       bcontent = ?, ");
		sql.append("       budate = sysdate ");
		sql.append(" where bnum = ? ");
		
		int rows = jt.update(sql.toString(), 
													boardDTO.getBcategory(),
													boardDTO.getBtitle(),
													boardDTO.getBcontent(),
													bnum);
		
		if(rows != 1) {
			 throw new IllegalArgumentException(bnum + " 번 게시글 번호를 찾을 수 없습니다!");
		}
		
		log.info("게시글 수정 rows:"+rows);
		return bnum;
	}
	
	//게시글 삭제
	@Override
	public void boardDel(Long bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from board ");
		sql.append(" where bnum = ? ");
		
		if(jt.update(sql.toString(), bnum) != 1) {
			throw new IllegalArgumentException(bnum + " 번 게시글 번호를 찾을 수 없습니다!");
		};
	}
	
	//카테고리별 게시글 리스트
	@Override
	public List<BoardDTO> list(String bcategory, int startRec, int endRec) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.* ");
		sql.append("  from (select row_number() over(order by bgroup desc, bstep asc) as num, ");
		sql.append("               bnum,    ");
		sql.append("               bmid,    ");
		sql.append("               bnickname,   ");
		sql.append("               bcategory,   ");
		sql.append("               btitle,    ");
		sql.append("               bhit,  ");
		sql.append("               bpnum,   ");
		sql.append("               bgroup,  ");
		sql.append("               bstep,   ");
		sql.append("               bindent,   ");
		sql.append("               bcdate,  ");
		sql.append("               budate   ");
		sql.append("          from board ");
		sql.append("         where bcategory = ? ) t1  ");
		sql.append(" where num between ? and ?  ");
		
		List<BoardDTO> list = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				bcategory, startRec, endRec
				);
		
		return list;
	}
	
	//게시글 카테고리별 검색결과 목록
	@Override
	public List<BoardDTO> list(SearchDTO searchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.* ");
		sql.append("  from (select row_number() over(order by bgroup desc, bstep asc) as num, ");
		sql.append("               bnum,    ");
		sql.append("               bmid,    ");
		sql.append("               bnickname,   ");
		sql.append("               bcategory,   ");
		sql.append("               btitle,    ");
		sql.append("               bhit,  ");
		sql.append("               bpnum,   ");
		sql.append("               bgroup,  ");
		sql.append("               bstep,   ");
		sql.append("               bindent,   ");
		sql.append("               bcdate,  ");
		sql.append("               budate   ");
		sql.append("          from board t1");
		sql.append("         where bcategory = ? ");
		
		switch (searchDTO.getSearchType()) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + searchDTO.getKeyword() + "%' ");
			sql.append("   or t1.bcontent like '%" + searchDTO.getKeyword() + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + searchDTO.getKeyword() + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + searchDTO.getKeyword() + "%' ");
			break;
		case "N": //별칭
			sql.append("and t1.bnickname  like '%" + searchDTO.getKeyword() + "%'" );
			break;
		case "A":  //전체			
			sql.append("and ( t1.btitle  like '%" + searchDTO.getKeyword() + "%' ");
			sql.append("   or t1.bcontent like '%" + searchDTO.getKeyword() + "%' ");
			sql.append("   or t1.bnickname like '%" + searchDTO.getKeyword() + "%' ");
			sql.append("   or t1.bmid like '%" + searchDTO.getKeyword() + "%' )");
			break;

		default:
			break;
		}				
		sql.append(") t1  ");		
		sql.append(" where num between ? and ?  ");
		
	
		List<BoardDTO> list = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				searchDTO.getScategory(), searchDTO.getStarcRec(), searchDTO.getEndRec()
				);	
		
		return list;
	}
	
	//게시글 상세
	@Override
	public BoardDTO boardDetail(Long bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bnum,  ");
		sql.append("       bmid,  ");
		sql.append("       bnickname,  ");
		sql.append("       bcategory,  ");
		sql.append("       btitle,  ");
		sql.append("       bcontent, ");
		sql.append("       bhit, ");
		sql.append("       bpnum, ");
		sql.append("       bgroup, ");
		sql.append("       bstep, ");
		sql.append("       bindent, ");
		sql.append("       bcdate, ");
		sql.append("       budate ");
		sql.append("  from board  ");
		sql.append(" where bnum = ? ");
		
		BoardDTO boardDTO =	jt.queryForObject(
			sql.toString(), 
			new BeanPropertyRowMapper<>(BoardDTO.class),
			bnum
		);
		return boardDTO;
	}


	
	//조회수 증가
	@Override
	public void updateBhit(Long bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("	 set bhit = bhit + 1 ");
		sql.append(" where bnum = ? ");
		
		jt.update(sql.toString(), bnum);
		
	}
	
	//게시판 전체 레코드수
	@Override
	public long totoalRecordCount() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("	from board ");
		
		long totalCount = jt.queryForObject(sql.toString(), Long.class);
		return totalCount;
	}
	
	//게시판 카테고리별 레코드수
	@Override
	public long totoalRecordCount(String category) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("	from board ");
		sql.append(" where bcategory = ?");
		
		long totalCount = jt.queryForObject(sql.toString(), Long.class, category);
		return totalCount;
	}
	
	//게시판 카테고리별 검색결과 레코드수
	@Override
	public long totoalRecordCount(String bcategory, String searchType, String keyword) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("	from board t1");
		sql.append(" where bcategory = ?");
		
		switch (searchType) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + keyword + "%' ");
			sql.append("   or t1.bcontent like '%" + keyword + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + keyword + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + keyword + "%' ");
			break;
		case "N": //별칭
			sql.append("and t1.bnickname  like '%" + keyword + "%'" );
			break;
		case "A":  //전체			
			sql.append("and ( t1.btitle  like '%" + keyword + "%' ");
			sql.append("   or t1.bcontent like '%" + keyword + "%' ");
			sql.append("   or t1.bnickname like '%" + keyword + "%') ");
			break;

		default:
			break;
		}				
		long totalCount = jt.queryForObject(sql.toString(), Long.class, bcategory);
		return totalCount;
	}
	
	// 내가 쓴 글
	// 1. 검색어 없을시
	
	// 내가 쓴 글 카테고리별 레코드수
	@Override
	public long myTotalRecordCount(String category, String mid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("	from board ");
		sql.append(" where bcategory = ? and bmid = ?");
		long totalCount = jt.queryForObject(sql.toString(), Long.class, category, mid);
		return totalCount;
	}
	// 내가 쓴 글 카테고리별 리스트
	@Override
	public List<BoardDTO> myList(String bcategory, String mid, int startRec, int endRec) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.* ");
		sql.append("  from (select row_number() over(order by bgroup desc, bstep asc) as num, ");
		sql.append("               bnum,    ");
		sql.append("               bmid,    ");
		sql.append("               bnickname,   ");
		sql.append("               bcategory,   ");
		sql.append("               btitle,    ");
		sql.append("               bhit,  ");
		sql.append("               bpnum,   ");
		sql.append("               bgroup,  ");
		sql.append("               bstep,   ");
		sql.append("               bindent,   ");
		sql.append("               bcdate,  ");
		sql.append("               budate   ");
		sql.append("          from board ");
		sql.append("         where bcategory = ? and bmid = ? ) t1  ");
		sql.append(" where num between ? and ?  ");
		
		List<BoardDTO> list = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				bcategory, mid, startRec, endRec
				);
		
		return list;
	}
	
	// 2. 검색어 있을시
	
	//게시판 카테고리별 검색결과 레코드수
	@Override
	public long myTotalRecordCount(String bcategory, String mid, String searchType, String keyword) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) ");
		sql.append("	from board t1");
		sql.append(" where bcategory = ? and bmid = ?");
		
		switch (searchType) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + keyword + "%' ");
			sql.append("   or t1.bcontent like '%" + keyword + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + keyword + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + keyword + "%' ");
			break;
		default:
			break;
		}				
		long totalCount = jt.queryForObject(sql.toString(), Long.class, bcategory, mid);
		return totalCount;
	}

	//게시글 카테고리별 검색결과 목록
	@Override
	public List<BoardDTO> myList(SearchDTO searchDTO, String mid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.* ");
		sql.append("  from (select row_number() over(order by bgroup desc, bstep asc) as num, ");
		sql.append("               bnum,    ");
		sql.append("               bmid,    ");
		sql.append("               bnickname,   ");
		sql.append("               bcategory,   ");
		sql.append("               btitle,    ");
		sql.append("               bhit,  ");
		sql.append("               bpnum,   ");
		sql.append("               bgroup,  ");
		sql.append("               bstep,   ");
		sql.append("               bindent,   ");
		sql.append("               bcdate,  ");
		sql.append("               budate   ");
		sql.append("          from board t1");
		sql.append("         where bcategory = ? and bmid = ? ");
		
		switch (searchDTO.getSearchType()) {
		case "TC": //제목+내용
			sql.append("and ( t1.btitle  like '%" + searchDTO.getKeyword() + "%' ");
			sql.append("   or t1.bcontent like '%" + searchDTO.getKeyword() + "%' ) ");
			break;
		case "T":	//제목
			sql.append("and t1.btitle  like '%" + searchDTO.getKeyword() + "%' ");
			break;
		case "C":	//내용
			sql.append("and t1.bcontent  like '%" + searchDTO.getKeyword() + "%' ");
			break;
		default:
			break;
		}				
		sql.append(") t1  ");		
		sql.append(" where num between ? and ?  ");
		
	
		List<BoardDTO> list = jt.query(
				sql.toString(), 
				new BeanPropertyRowMapper<>(BoardDTO.class),
				searchDTO.getScategory(), mid, searchDTO.getStarcRec(), searchDTO.getEndRec()
				);	
		
		return list;
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
