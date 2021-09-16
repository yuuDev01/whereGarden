package com.kh.wheregarden.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.wheregarden.domain.member.dto.MemberDTO;
import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.web.form.Gender;
import com.kh.wheregarden.web.form.login.LoginForm;
import com.kh.wheregarden.web.form.login.LoginMember;
import com.kh.wheregarden.web.form.member.EditForm;
import com.kh.wheregarden.web.form.member.JoinForm;
import com.kh.wheregarden.web.form.member.MyinfoPw;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private final MemberSVC memberSVC;
	
	@ModelAttribute("gender")
	public Gender[] gender() {
		System.out.println(Gender.values());
		return Gender.values();
	}	

	@GetMapping("/myinfo")
	public String mypage(Model model) {
		
		model.addAttribute("myinfoPw", new MyinfoPw());
		
		return "mypage/myinfo";
	}
	
	/**
	 * 비밀번호 입력 처리
	 * @param myinfoPw
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@PatchMapping("/myinfo")
	public String mypagePw(
			@Valid @ModelAttribute MyinfoPw myinfoPw,
			BindingResult bindingResult,
			HttpServletRequest request) {
		log.info("회원수정처리 호출됨");
		HttpSession session = request.getSession(false);
		LoginMember loginMember = 
				(LoginMember)session.getAttribute("loginMember");
		log.info("회원 수정 처리:{}"+loginMember.toString());
		//세션이 없으면 로그인 페이지로 이동
		if(loginMember == null) return "redirect:/login";
		
		//비밀번호를 잘못입력했을경우
		if(!memberSVC.isMeMember(loginMember.getId(), myinfoPw.getMpw())) {
			bindingResult.rejectValue("mpw", "mpw", "비밀번호가 잘못입력되었습니다.");
			return "mypage/myinfo";
		}

		if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "mypage/myinfo";
		}
		
		return "redirect:/mypage/edit";
	}
	
	@GetMapping("/edit")
	public String editForm(
			HttpServletRequest request,
			Model model) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember
		= (LoginMember)session.getAttribute("loginMember");
	
	//세션이 없으면 로그인 페이지로 이동
		if(loginMember == null) return "redirect:/login";
		
	//회원정보 가져오기
			MemberDTO memberDTO =  memberSVC.findByEmail(loginMember.getId());
			EditForm editForm = new EditForm();
			BeanUtils.copyProperties(memberDTO, editForm);
			model.addAttribute("editForm", editForm);
			return "mypage/memberEditForm";
	}
	
	@PatchMapping("/edit")
	public String edit(
			@Valid @ModelAttribute EditForm editForm,
			BindingResult bindingResult,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember 
		= (LoginMember)session.getAttribute("loginMember");
		log.info("수정입력처리 확인");
		
	
	//세션이 없으면 로그인 페이지로 이동
	if(loginMember == null) return "redirect:/login";
	
	//비밀번호 확인 체크
		if(!editForm.getMpw().equals(editForm.getMpwchk())) {
			//bindingResult.reject("error.mypage.editForm", "비밀번호가 다릅니다.");
			bindingResult.rejectValue("mpwchk", "mpwchk", "비밀번호가 일치하지 않습니다.");
			return "mypage/memberEditForm";			
		}
		
	//닉네임 존재유무
//		if(memberSVC.isExistNickname(editForm.getMnickname())) {
//			bindingResult.reject("error.mypage.editForm", "동일한 닉네임이 존재합니다");
//			return "mypage/memberEditForm";
//		}
		
	if(bindingResult.hasErrors()) {
		log.info("errors={}",bindingResult);
		log.info("bindingResult.hasErrors() 확인");
		return "mypage/memberEditForm";
	}
	
	log.info("입력받은 memberDTO:{}",editForm);
	
	MemberDTO mdto = new MemberDTO();
	BeanUtils.copyProperties(editForm, mdto);
	memberSVC.update(loginMember.getId(), mdto);
	return "redirect:/mypage/myinfo";
	}
	

	@GetMapping("/out")
	public String out(
			HttpServletRequest request,
			Model model) {
		log.info("회원탈퇴");
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		memberSVC.outMember(loginMember.getId());
		log.info("탈퇴완료");
		//세션제거
		session.invalidate();

		return "home";
	}
}

