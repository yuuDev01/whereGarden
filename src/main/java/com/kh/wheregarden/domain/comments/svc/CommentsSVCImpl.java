package com.kh.wheregarden.domain.comments.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.board.dao.BoardDAO;
import com.kh.wheregarden.domain.comments.dao.CommentsDAO;
import com.kh.wheregarden.domain.comments.dto.CommentsDTO;
import com.kh.wheregarden.domain.common.dao.UpLoadFileDAO;
import com.kh.wheregarden.domain.common.file.FileStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC {
	
	private final CommentsDAO commentsDAO;

	//게시글에 댓글 불러오기
	@Override
	public List<CommentsDTO> showComment(Long cbnum) {
		// TODO Auto-generated method stub
		return commentsDAO.showComment(cbnum);
	}

	//댓글 작성
	@Override
	public Long writeComment(CommentsDTO commentsDTO) {
		// TODO Auto-generated method stub
		return commentsDAO.writeComment(commentsDTO);
	}



	//댓글 수정
	@Override
	public String modifyComment(String modifiedContent, Long cnum, String id) {
		// TODO Auto-generated method stub
		return commentsDAO.modifyComment(modifiedContent, cnum, id);
	}

	//댓글 삭제
	@Override
	public void delComment(Long cnum) {
		commentsDAO.delComment(cnum);
	}
	
	
	// 부모댓글 찾기
	@Override
	public CommentsDTO findParentComment(Long cnum) {
		// TODO Auto-generated method stub
		return commentsDAO.findParentComment(cnum);
	}
	
	//답글 작성
	@Override
	public Long writeReplyComment(CommentsDTO commentsDTO) {
		// TODO Auto-generated method stub
		return commentsDAO.writeReplyComment(commentsDTO);
	}



}
