package com.kh.portfolio.domain.board.svc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

//import com.kh.portfolio.domain.board.FileStore;
import com.kh.portfolio.domain.board.dao.BoardDAO;
import com.kh.portfolio.domain.board.dto.BoardDTO;
import com.kh.portfolio.domain.common.dao.UpLoadFileDAO;
import com.kh.portfolio.domain.common.dto.UpLoadFileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC{

	private final BoardDAO boardDAO;
	private final UpLoadFileDAO upLoadFileDAO;

	//원글작성
	@Override
	public Long boardWrite(BoardDTO boardDTO) {
		//게시글작성
		Long bnum = boardDAO.boardWrite(boardDTO);

		//첨부파일 메타정보 저장
		upLoadFileDAO.addFiles(
				convert(bnum, boardDTO.getBcategory(), boardDTO.getFiles())
		);
		return bnum;
	}

	private List<UpLoadFileDTO> convert(
			Long bnum,String bcategory,List<UpLoadFileDTO> files) {
		for(UpLoadFileDTO ele : files) {
			ele.setRid(String.valueOf(bnum));
			//ele.setCode(bcategory);
		}
		return files;
	}

	//답글 작성
	@Override
	public Long ReplyWrite(BoardDTO boardDTO) {
		
		return boardDAO.ReplyWrite(boardDTO);
	}

	//게시글 수정
	@Override
	public Long boardModify(Long bnum, BoardDTO boardDTO) {
		
		return boardDAO.boardModify(bnum, boardDTO);
	}

	//게시글 삭제
	@Override
	public void boardDel(Long bnum) {
		boardDAO.boardDel(bnum);
	}

	//카테고리별 게시글 리스트
	@Override
	public List<BoardDTO> list(String bcategory, int startRec, int endRec) {
		
		return boardDAO.list(bcategory, startRec, endRec);
	}

	//게시글 상세
	@Override
	public BoardDTO boardDetail(Long bnum) {
		//게시글 가져오기
		BoardDTO boardDTO = boardDAO.boardDetail(bnum);
		
		//첨부파일 가져오기
		boardDTO.setFiles(
//				upLoadFileDAO.getFiles(
//						String.valueOf(boardDTO.getBnum()), boardDTO.getBcategory()));
				upLoadFileDAO.getFiles(String.valueOf(boardDTO.getBnum())));
		
		//조회수증가
		boardDAO.updateBhit(bnum);
		return boardDTO;
	}
	
	//게시판 전체 레코드수
	@Override
	public long totoalRecordCount() {

		return boardDAO.totoalRecordCount();
	}
	//게시판 카테고리별 레코드 총수 
	@Override
	public long totoalRecordCount(String category) {

		return boardDAO.totoalRecordCount(category);
	}
	

}
