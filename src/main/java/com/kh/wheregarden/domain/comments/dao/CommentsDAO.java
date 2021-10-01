package com.kh.wheregarden.domain.comments.dao;

import java.util.List;

import com.kh.wheregarden.domain.board.dto.BoardDTO;
import com.kh.wheregarden.domain.comments.dto.CommentsDTO;

public interface CommentsDAO {

	//게시글에 댓글 불러오기
	List<CommentsDTO> showComment(Long cbnum);
	
	//댓글 작성
	Long writeComment(CommentsDTO commentsDTO);
	
	//댓글 수정
	String modifyComment(String modifiedContent, Long cnum, String id);
	
	//댓글 삭제
	void delComment(Long cnum);
	
	
	//부모 댓글 불러오기
	CommentsDTO findParentComment(Long cnum);
	
	//대댓글 작성
	Long writeReplyComment(CommentsDTO commentsDTO);
}
