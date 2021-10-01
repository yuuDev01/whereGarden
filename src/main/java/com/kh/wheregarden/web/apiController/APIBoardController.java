package com.kh.wheregarden.web.apiController;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.wheregarden.domain.board.svc.BoardSVC;
import com.kh.wheregarden.domain.comments.dao.CommentsDAO;
import com.kh.wheregarden.domain.common.dao.UpLoadFileDAO;
import com.kh.wheregarden.domain.common.file.FileStore;
import com.kh.wheregarden.web.api.CommentsModiReq;
import com.kh.wheregarden.web.api.JsonResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class APIBoardController {

	private final BoardSVC boardSVC;
	private final UpLoadFileDAO upLoadFileDAO;
	private final FileStore fileStore;
	private final CommentsDAO commentsDAO;
	
	//댓글 수정
	@PatchMapping("/modifyComment")
	public JsonResult<String> modifyComment(
			@RequestBody CommentsModiReq commentsModiReq,
			BindingResult bindingResult
			) {

		log.info("commentsModiReq:{}",commentsModiReq);
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String modifiedContent = commentsDAO.modifyComment(commentsModiReq.getModifiedContent(),
				commentsModiReq.getCnum(), commentsModiReq.getId());
		
		return new JsonResult<String>("00","ok",modifiedContent);
	}
	
	
	//게시글 삭제
	@DeleteMapping("/{bnum}")
	public JsonResult<String> delItem(@PathVariable Long bnum) {

		boardSVC.boardDel(bnum);
		return new JsonResult<String>("00", "ok", String.valueOf(bnum));
	}
	
	//첨부파일 삭제 by fid
	@DeleteMapping("/attach/{sfname}")
	public JsonResult<String> delFile(@PathVariable String sfname){
		
		if(fileStore.deleteFile(sfname)) {
			upLoadFileDAO.deleteFileBySfname(sfname);
		}else {
			return new JsonResult<String>("01","nok","파일삭제 실패!");
		}
		
		return new JsonResult<String>("00","ok","파일삭제 성공");
	}
}





