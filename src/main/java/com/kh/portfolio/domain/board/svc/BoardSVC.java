package com.kh.portfolio.domain.board.svc;

import java.io.IOException;
import java.util.List;

import com.kh.portfolio.domain.board.dto.BoardDTO;

public interface BoardSVC {
	
	/**
	 * 게시글 작성
	 * @param boardDTO
	 * @return 게시글 고유번호
	 */
	Long boardWrite(BoardDTO boardDTO);
	/**
	 * 답글 작성
	 * @param boardDTO
	 * @return 답글 고유번호
	 */
	Long ReplyWrite(BoardDTO boardDTO);
	/**
	 * 게시글 수정
	 * @param bnum
	 * @param boardDTO
	 * @return
	 */
	Long boardModify(Long bnum, BoardDTO boardDTO);	
	/**
	 * 게시글 삭제
	 * @param bnum
	 */
	void boardDel(Long bnum);
	
	/**
	 * 카테고리별 게시글 리스트
	 * @param category
	 * @param startRec
	 * @param endRec
	 * @return
	 */
	List<BoardDTO> list(String bcategory, int startRec, int endRec);
	
	/**
	 * 게시글 상세
	 * @param bnum
	 * @return
	 */
	BoardDTO boardDetail(Long bnum);
	
	/**
	 * 게시판 전체 레코드 총수 
	 * @return
	 */
	long totoalRecordCount();
	/**
	 * 게시판 카테고리별 레코드 총수 
	 * @return
	 */
	long totoalRecordCount(String category);

}
