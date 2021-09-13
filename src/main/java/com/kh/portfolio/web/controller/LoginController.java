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

	//로그인 페이지
	@GetMapping("/loginPage")
	public String loginPage(@ModelAttribute LoginForm loginForm) {
		
		return "member/loginPage";
	}
	
	//로그인 처리
	@PostMapping("")
	public String login(
			@Valid
			@ModelAttribute LoginForm loginForm,
			BindingResult bindingResult,
			HttpServletRequest request
			) {
		
		MemberDTO loginMemberDTO = memberSVC.isOurMember(loginForm.getLid(), loginForm.getLpw());
		
		if(loginMemberDTO == null) bindingResult.reject("loginError", "회원정보가 없습니다");
		
		//글로벌오류 체크
		if(bindingResult.hasErrors()) {
			log.info("BindingResult:{}",bindingResult);
			return "loginForm";
		}
		
		//LoginMember클래스에 로그인 된 정보를 가지고 새로운 객체 생성
		LoginMember loginMember = new LoginMember(
				loginMemberDTO.getMid(),loginMemberDTO.getMnickname(), "회원");
		
		//로긴성공
		HttpSession session = request.getSession(true);		//true : 세선이 없으면 만들고 있으면 그 세션반환
		session.setAttribute("loginMember", loginMember);
		log.info("로그인 된 memberDTO:{}",loginMemberDTO);
		log.info("로그인 된 loginMember:{}",loginMember);
		return "redirect:/";
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
