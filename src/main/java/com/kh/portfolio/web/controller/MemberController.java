package com.kh.portfolio.web.controller;

import javax.persistence.metamodel.SetAttribute;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.portfolio.domain.member.dto.MemberDTO;
import com.kh.portfolio.domain.member.svc.MemberSVC;
import com.kh.portfolio.web.form.member.Gender;
import com.kh.portfolio.web.form.member.JoinForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	//service 객체 생성
	private final MemberSVC memberSVC;
	
	//enum gender 모델 객체
	@ModelAttribute("gender")
	public Gender[] gender() {
		System.out.println(Gender.values());
		return Gender.values();
	}
	
	/**
	 * url : /member/joinPage
	 * @return 회원가입 종류 선택 페이지
	 */
	@GetMapping("/joinPage")
	public String joinPage() {
		
		return "member/joinPage";
	}
	
	/**
	 * url : /member/joinFormPage
	 * valid를 정의한 JoinForm.class를 모델객체에 불러옴
	 * @return 어딜,가든 사이트 회원가입을 위한 정보입력 페이지
	 */
	@GetMapping("/joinFormPage")
	public String joinFormPage(@ModelAttribute JoinForm joinForm) {
		
		return "member/joinForm";
	}

	/**
	 * 회원가입 처리
	 * @return 회원가입 완료 후 메인페이지
	 */
	@PostMapping("/joinFormWrite")
	public String joinFormWrite(
			@Valid
			@ModelAttribute JoinForm joinForm,
			BindingResult bindingResult,
			Model model
			) {
		
		if(!joinForm.getJpw().equals(joinForm.getJpwChk())) {
			bindingResult.rejectValue("jpwChk","jpwChk", "비밀번호가 맞지 않습니다");
		}
		
//		//회원 존재유무
//		if(memberSVC.isExistEmail(joinForm.getJid())) {
//			bindingResult.reject("isExistEmail", "동일한 이메일이 존재합니다");
//		}
		
		if(joinForm.getJid() == "") {
			bindingResult.reject("joinFormChk", "누락된 곳이 있습니다.");	
		}
		
		if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "member/joinForm";
		}
		
		log.info("입력된 회원정보:{}",joinForm);
		MemberDTO newMemberDTO = new MemberDTO(joinForm.getJid(), joinForm.getJpw(), joinForm.getJquestion(), joinForm.getJanswer(),
												joinForm.getJname(), joinForm.getJbirth(), (joinForm.getGender()).getDecode(),
												joinForm.getJtel(), joinForm.getJaddress(), joinForm.getJnickname(), 0);
		memberSVC.createMember(newMemberDTO);
		
		return "/index";
	}
	
	/**
	 * 내 정보 조회
	 */
	@GetMapping("/detailMyInfo/{mid:.+}")
	public String detailMyInfoView(
			@PathVariable("mid") String mid,
			Model model
			) {
		
		MemberDTO findedMemberDTO = memberSVC.findMemberById(mid);
		model.addAttribute("findedMemberDTO", findedMemberDTO);
		log.info("id로 찾은 회원정보:{}",findedMemberDTO);
		
		return "member/detailMyInfo";
	}
	
	/**
	 * 내 정보 수정양식
	 */
	@GetMapping("/modifyDetailMyInfoView/{mid:.+}")
	public String modifyDetailMyInfoView(
			@PathVariable("mid") String mid,
			Model model
			) {
		
		MemberDTO findedMemberDTO = memberSVC.findMemberById(mid);
		model.addAttribute("findedMemberDTO", findedMemberDTO);
		log.info("id로 찾은 회원정보수정폼 : {}",findedMemberDTO);
		
		return "member/modifyDetailMyInfo";
	}
	/**
	 * 내 정보 수정
	 * /member/detailMyInfoModify/{mid:.+}
	 * @return 
	 */
	@PostMapping("/modifyDetailMyInfo/{mid:.+}")
	public String modifyDetailMyInfo(
			@PathVariable("mid") String mid,
			@ModelAttribute MemberDTO memberDTO,
			Model model,
			RedirectAttributes redirectAttributes
			) {

		MemberDTO modifiedMemberDTO = memberSVC.modifyMember(mid, memberDTO);
		model.addAttribute("modifiedMemberDTO", modifiedMemberDTO);
		redirectAttributes.addAttribute("mid", modifiedMemberDTO.getMid());

		log.info("수정된 회원정보 : {}", modifiedMemberDTO);
		return "redirect:/member/detailMyInfo/{mid}";
	}

	/**
	 * 회원탈퇴 처리
	 * 실습으로 해본 상품리스트가 회원에는 없기 때문에 직접 url에 mid 인자를 줘서 삭제되는지 확인
	 */
	@GetMapping("/del/{mid:.+}")
	public String deleteMember(@PathVariable("mid") String mid) {
		
		memberSVC.deleteMember(mid);
		return "index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}