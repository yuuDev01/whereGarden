package com.kh.wheregarden.domain.board.svc;

import java.io.IOException;
import java.util.List;

import com.kh.wheregarden.domain.board.dto.BoardDTO;
import com.kh.wheregarden.domain.board.dto.SearchDTO;

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
	Long replyWrite(BoardDTO boardDTO);
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
	 * 게시글 카테고리별 검색결과 목록
	 * @param searchDTO
	 * @return
	 */
	List<BoardDTO> list(SearchDTO searchDTO);
	
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
	/**
	 * 게시판 카테고리별 검색결과 총수 
	 * @return
	 */
	public long totoalRecordCount(String bcategory, String searchType, String keyword);

	
	// 내가 쓴 글
	// 1. 검색어 없을시
	/**
	 * 내가 쓴 글 전체 카테고리별 레코드 총수 
	 * @return
	 */
	long myTotalRecordCount(String category, String mid);
	/**
	 * 내가 쓴 글 카테고리별 게시글 리스트
	 * @param category
	 * @param startRec
	 * @param endRec
	 * @return
	 */
	List<BoardDTO> myList(String bcategory, String mid, int startRec, int endRec);
	
	//2. 검색어 있을시
	/**
	 * 내가 쓴 글 전체 카테고리별 검색된 레코드 총 수 
	 * @return
	 */
	long myTotalRecordCount(String bcategory, String mid, String searchType, String keyword);
	/**
	 * 내가 쓴 글 카테고리별 검색결과 목록
	 * @param searchDTO
	 * @return
	 */
	List<BoardDTO> myList(SearchDTO searchDTO, String mid);
	
}