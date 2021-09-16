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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.portfolio.domain.board.dao.BoardDAO;
import com.kh.portfolio.domain.board.dto.BoardDTO;
import com.kh.portfolio.domain.board.dto.SearchDTO;
import com.kh.portfolio.domain.board.svc.BoardSVC;
import com.kh.portfolio.domain.common.dto.MetaOfUploadFile;
import com.kh.portfolio.domain.common.dto.UpLoadFileDTO;
import com.kh.portfolio.domain.common.file.FileStore;
import com.kh.portfolio.domain.common.paging.FindCriteria;
import com.kh.portfolio.domain.common.paging.PageCriteria;
import com.kh.portfolio.web.form.board.ModifyForm;
import com.kh.portfolio.web.form.board.ReplyForm;
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
	private final BoardDAO boardDAO;
	private final FileStore fileStore;
//	@Autowired @Qualifier("pc10")
//	private PageCriteria pc;
	@Autowired
	@Qualifier("fc10")
	private FindCriteria fc;
	
	//게시글 목록
	@GetMapping({"/boardList",
							 "/boardList/{reqPage}",
							 "/boardList/{reqPage}/{searchType}/{keyword}"})
	public String list(
			@RequestParam(required = false) String category,
			@PathVariable(required = false) Integer reqPage,
			@PathVariable(required = false) String searchType,
			@PathVariable(required = false) String keyword,	
			Model model
			) {
		
		List<BoardDTO> list = null;
		
		//요청페이지가 없으면 1페이지로
		if(reqPage == null) reqPage = 1;
		//사용자가 요청한 페이지번호
		fc.getRc().setReqPage(reqPage);	
		
		//검색어 유무
		if((searchType == null || searchType.equals(""))
				&& (keyword == null || keyword.equals(""))) {
			
			//게시판 전체레코드수 설정
			fc.setTotalRec(boardSVC.totoalRecordCount(category));
			
			list = boardSVC.list(
					category,
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec());
			log.info("boardList:{}",list);
		}else {
			//게시판 전체레코드수
			fc.setTotalRec(boardSVC.totoalRecordCount(category,searchType,keyword));
			
			list = boardSVC.list(
					new SearchDTO(
							category, 
							fc.getRc().getStartRec(), fc.getRc().getEndRec(), 
							searchType, keyword)
			);						
		}
		
		fc.setSearchType(searchType);
		fc.setKeyword(keyword);
				
		model.addAttribute("boardList", list);
		model.addAttribute("fc", fc);
		model.addAttribute("category",category);
		
		return "board/boardList";
	}
	
	//게시글 상세 조회
	@GetMapping("/{bnum}")
	public String boardDetail(
			@PathVariable Long bnum,
			Model model) {
		
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
	
	//게시글 수정 처리
	@PatchMapping("/boardModify/{bnum}")
	public String edit(
			@PathVariable Long bnum,
			@Valid @ModelAttribute ModifyForm modifyForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			log.info("게시글수정처리오류:{}",bindingResult);
			return "bbs/editForm";
		}
		
		BoardDTO boardDTO = new BoardDTO();
		
		//첨부파일 파일시스템에 저장후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(modifyForm.getFiles());
		//UploadFileDTO 변환
		boardDTO.setFiles(convert(storedFiles));		
		BeanUtils.copyProperties(modifyForm, boardDTO);
		
		Long modifyedBnum = boardSVC.boardModify(bnum, boardDTO);
		redirectAttributes.addAttribute("bnum", modifyedBnum);
		
		return "redirect:/board/{bnum}";
	}
	
	//답글 작성 양식
	@GetMapping("/replyQnA/{bnum}")
	public String replyForm(
			@PathVariable Long bnum,
			Model model,
			HttpServletRequest request) {		
		
		ReplyForm replyForm = new ReplyForm();
		
		//세션에서 회원 id,email,nickname가져오기
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = 
					(LoginMember)session.getAttribute("loginMember");
			
			replyForm.setBmid(loginMember.getId());
			replyForm.setBnickname(loginMember.getNickname());
		}
		
		//부모글의 글번호, 분류코드, 제목 가져오기
		BoardDTO pBoardDTO = boardSVC.boardDetail(bnum);
		replyForm.setBpnum(pBoardDTO.getBnum());
		replyForm.setBcategory(pBoardDTO.getBcategory());
		replyForm.setBtitle("답글 : " + pBoardDTO.getBtitle());
		
		model.addAttribute("replyForm", replyForm);
		
		return "board/replyWrite";
	}
	
	//답글 작성 처리
	@PostMapping("/replyQnA/{bnum}")
	public String reply(
			@PathVariable("bnum") Long bpnum,  //부모글
			@Valid @ModelAttribute ReplyForm replyForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
	
		if(bindingResult.hasErrors()) {
			return "board/replyForm";
		}
		
		BoardDTO replyboardDTO = new BoardDTO();
		BeanUtils.copyProperties(replyForm, replyboardDTO);
		
		//부모글의 bnum, bgroup, bstep, bindent
		BoardDTO pboardDTO = boardSVC.boardDetail(bpnum);
		replyboardDTO.setBpnum(pboardDTO.getBnum());			//부모글번호 찾아와서 답글의 부모글번호에 넣기
		replyboardDTO.setBgroup(pboardDTO.getBgroup());		//부모글그룹 찾아와서 답글의 그룹에 넣기
		replyboardDTO.setBstep(pboardDTO.getBstep());			//
		replyboardDTO.setBindent(pboardDTO.getBindent());
		
		//첨부파일 파일시스템에 저장후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(replyForm.getFiles());
		//UploadFileDTO 변환
		replyboardDTO.setFiles(convert(storedFiles));
		
		Long rbnum = boardSVC.replyWrite(replyboardDTO);
		
		redirectAttributes.addAttribute("bnum", rbnum);
		return "redirect:/board/{bnum}";
	}
	
	//내가 쓴 글 목록
	@GetMapping({"/myBoardList",
							 "/myBoardList/{reqPage}",
							 "/myBoardList/{reqPage}/{searchType}/{keyword}"})
	public String myBoardList(
			@RequestParam(required = false) String category,
			@PathVariable(required = false) Integer reqPage,
			@PathVariable(required = false) String searchType,
			@PathVariable(required = false) String keyword,
			HttpServletRequest request,
			Model model
			) {
		
		//세션 가져오기
		HttpSession session = request.getSession(false);
		//if(session == null) return "redirect:/login";
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		log.info("로그인 된 아이디:{}",loginMember.getId());
		
		List<BoardDTO> list = null;
		
		//요청페이지가 없으면 1페이지로
		if(reqPage == null) reqPage = 1;
		//사용자가 요청한 페이지번호
		fc.getRc().setReqPage(reqPage);	
		
		//검색어 유무
		if((searchType == null || searchType.equals(""))
				&& (keyword == null || keyword.equals(""))) {
			
			//게시판 전체레코드수 설정
			fc.setTotalRec(boardSVC.myTotalRecordCount(category, loginMember.getId()));
			log.info("찾은 레코드수 :{}",boardSVC.myTotalRecordCount(category, loginMember.getId()));
			list = boardSVC.myList(
					category,
					loginMember.getId(),
					fc.getRc().getStartRec(),
					fc.getRc().getEndRec());
			log.info("검색어 없는 myBoardList:{}",list);
		}
		else {
			//게시판 전체레코드수
			fc.setTotalRec(boardSVC.myTotalRecordCount(category, loginMember.getId(), searchType, keyword));
			
			list = boardSVC.myList(
					new SearchDTO(
							category, 
							fc.getRc().getStartRec(), fc.getRc().getEndRec(), 
							searchType, keyword)
					, loginMember.getId()
			);
			log.info("검색어 있는 myBoardList:{}",list);
		}
		
		fc.setSearchType(searchType);
		fc.setKeyword(keyword);
				
		model.addAttribute("boardList", list);
		model.addAttribute("fc", fc);
		model.addAttribute("category",category);
		
		return "board/myBoardList";
	}
}
