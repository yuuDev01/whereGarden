package com.kh.portfolio.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.portfolio.domain.member.dto.MemberDTO;
import com.kh.portfolio.domain.member.svc.MemberSVC;
import com.kh.portfolio.web.form.login.LoginForm;
import com.kh.portfolio.web.form.login.LoginMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	
	private final MemberSVC memberSVC;

	//주소페이지
	@GetMapping("/addressAPI")
	public String addressAPI() {
		
		return "addressAPI";
	}
	
	//로그인 페이지
	@GetMapping("/loginPage")
	public String loginPage(@ModelAttribute LoginForm loginForm) {
		
		return "member/loginPage";
	}
	
	//로그인 처리
	@PostMapping("/loginPage")
	public String login(
			@Valid
			@ModelAttribute LoginForm loginForm,
			BindingResult bindingResult,
			@RequestParam(name="redirectUrl",required = false) String redirectUrl,
			HttpServletRequest request
			) {
		
		MemberDTO loginMemberDTO = memberSVC.isOurMember(loginForm.getLid(), loginForm.getLpw());
		
		if(loginMemberDTO == null) bindingResult.reject("loginError", "회원정보가 없습니다");
		
		//글로벌오류 체크
		if(bindingResult.hasErrors()) {
			log.info("BindingResult:{}",bindingResult);
			return "loginForm";
		}
		
			//세션생성
			//세션이 있으면 가져오고 없으면 새롭게 생성
			HttpSession session =request.getSession(true);
			LoginMember loginMember = new LoginMember(
					loginMemberDTO.getMid(),	loginMemberDTO.getMnickname(), "회원");		
			session.setAttribute("loginMember", loginMember);
			log.info("redirectUrl:{}",redirectUrl);
			return "redirect:"+redirectUrl;
		}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		if(session !=null ) {
			session.invalidate(); //세션제거
		}
		return "index";
	}
}
