package com.kh.portfolio.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.portfolio.domain.board.dto.BoardDTO;
import com.kh.portfolio.domain.board.svc.BoardSVC;
import com.kh.portfolio.domain.common.dto.MetaOfUploadFile;
import com.kh.portfolio.domain.common.dto.UpLoadFileDTO;
import com.kh.portfolio.domain.common.file.FileStore;
import com.kh.portfolio.domain.common.paging.PageCriteria;
import com.kh.portfolio.web.form.board.WriteForm;
import com.kh.portfolio.web.form.login.LoginMember;
import com.kh.portfolio.web.form.member.Gender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardSVC boardSVC;
	private final FileStore fileStore;
	@Autowired @Qualifier("pc10")
	private PageCriteria pc;
	
	//게시글 목록
	@GetMapping({"/boardList",
							 "/boardList/{reqPage}"})
	public String list(
			@RequestParam(required = false) String category,
			@PathVariable(required = false) Integer reqPage,
			Model model
			) {
		
		List<BoardDTO> list = null;
		
		//요청페이지가 없으면 1페이지로
		if(reqPage == null) reqPage = 1;
		
		//전체조회
		if(category == null) {
			log.info("카테고리가 null일때(사실 카테고리가 없을 경우는 없게 코딩 예정이라 필요없는 분기문)");
			//사용자가 요청한 페이지번호
			pc.getRc().setReqPage(reqPage);	
			//게시판 전체레코드수
			pc.setTotalRec(boardSVC.totoalRecordCount());
			
			list = boardSVC.list(
					"freeBoard",
					pc.getRc().getStartRec(),
					pc.getRc().getEndRec());	
			
		//카테고리별 조회	
		}else {
			//사용자가 요청한 페이지번호
			pc.getRc().setReqPage(reqPage);	
			//게시판 전체레코드수
			pc.setTotalRec(boardSVC.totoalRecordCount(category));
			
			list = boardSVC.list(
					category,
					pc.getRc().getStartRec(),
					pc.getRc().getEndRec());			
		}
		log.info("게시글 목록:{}",list);
		model.addAttribute("boardList", list);
		model.addAttribute("pc", pc);
		model.addAttribute("category",category);
		
		return "board/boardList";
	}
	
	//게시글 상세 조회
	@GetMapping("/{bnum}")
	public String boardDetail(
			@PathVariable Long bnum,
			Model model) {
		
		log.info("게시글 상세조회:{}",boardSVC.boardDetail(bnum));
		model.addAttribute("boardDetail", boardSVC.boardDetail(bnum));
		
		return "board/boardDetail";
	}
	
	//게시글 작성 양식
	@GetMapping("/boardWrite")
	public String boardWrite(
			@RequestParam String category,
			Model model,
			HttpServletRequest request) {
		
		WriteForm writeForm = new WriteForm();
		
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
			
			writeForm.setBmid(loginMember.getId());
			writeForm.setBnickname(loginMember.getNickname());
			writeForm.setBcategory(category);
			}
//		else {
//			return "redirect:/login/loginPage";
//		}
		
		model.addAttribute("writeForm", writeForm);
		model.addAttribute("category", category);
		
		return "board/boardWrite";
	}


	//게시글 작성 처리
	@PostMapping("/boardWrite")
	public String write(
			//@RequestParam String cate,
			@Valid @ModelAttribute WriteForm writeForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
	
//		if(bindingResult.hasErrors()) {
//			return "board/boardWrite";
//		}
		
		log.info("writeForm:{}",writeForm);
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(writeForm, boardDTO);
		
		//첨부파일 파일시스템에 저장후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(writeForm.getFiles());
		//UploadFileDTO 변환
		boardDTO.setFiles(convert(storedFiles));
		
		log.info("저장 할 게시글:{}",boardDTO);					// 여까이 잘 됨 rid가 없는거 빼곤
		
		Long bnum = boardSVC.boardWrite(boardDTO);
		
		redirectAttributes.addAttribute("bnum", bnum);
		return "redirect:/board/{bnum}";
	}
	
	private UpLoadFileDTO convert(MetaOfUploadFile attatchFile) {
		UpLoadFileDTO uploadFileDTO = new UpLoadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}
	
	private List<UpLoadFileDTO> convert(List<MetaOfUploadFile> uploadFiles) {
		List<UpLoadFileDTO> list = new ArrayList<>();
	
		for(MetaOfUploadFile file : uploadFiles) {
			UpLoadFileDTO uploadFIleDTO = convert(file);
			list.add( uploadFIleDTO );
		}		
		return list;
	}

	//게시글 수정 양식
	@GetMapping("/boardModify/{bnum}")
	public String editForm(
			@PathVariable Long bnum,
			Model model) {
			
		model.addAttribute("modifyForm", boardSVC.boardDetail(bnum)) ;
		return "board/boardModify";
	}
	
//	//게시글 수정 처리
//	@PatchMapping("/{bnum}/edit")
//	public String edit(
//			@PathVariable Long bnum,
//			@Valid @ModelAttribute EditForm editForm,
//			BindingResult bindingResult,
//			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
//		
//		if(bindingResult.hasErrors()) {
//			log.info("게시글수정처리오류:{}",bindingResult);
//			return "bbs/editForm";
//		}
//		
//		BoardDTO boardDTO = new BoardDTO();
//		
//		//첨부파일 파일시스템에 저장후 메타정보 추출
//		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(editForm.getFiles());
//		//UploadFileDTO 변환
//		boardDTO.setFiles(convert(storedFiles));		
//		BeanUtils.copyProperties(editForm, boardDTO);
//		
//		Long modifyedBnum = boardSVC.modifyItem(bnum, boardDTO);
//		redirectAttributes.addAttribute("bnum", modifyedBnum);
//		
//		return "redirect:/bbs/{bnum}";
//	}
}
