package com.kh.wheregarden.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.wheregarden.domain.member.dto.MemberDTO;
import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.web.form.login.LoginForm;
import com.kh.wheregarden.web.form.login.LoginMember;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
public class HomeController {

	private final MemberSVC memberSVC;

	
	//초기화면
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	//로그인 양식
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());

		return "loginForm";
	}
	
	
//로그인 처리
	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute LoginForm loginForm, 
			BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		
		log.info("LoginForm:{}",loginForm);
		
		//회원여부확인
		MemberDTO memberDTO = 
				memberSVC.isLogin(loginForm.getMid(), loginForm.getMpw());
		
		//아이디는 맞는데 비밀번호가 틀렸을때
		if(memberSVC.isExistEmail(loginForm.getMid()) && memberDTO == null) {
			bindingResult.reject("error.login", "비밀번호가 틀렸습니다");
			return "loginForm";
		}
		
		
		if(memberDTO == null) {
			bindingResult.reject("error.login", "회원정보가 없습니다");
			return "loginForm";
		}
		
		//세션생성
		//세션이 있으면 가져오고 없으면 새롭게 생성
		HttpSession session =request.getSession(true);
		LoginMember loginMember = new LoginMember(
			memberDTO.getMid(), memberDTO.getMnickname(), "회원");		
		session.setAttribute("loginMember", loginMember );
		
		return "redirect:/";
}

	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	//세션이 존재하면 가져오고 없으면 세션을 생성하지 않는다.
		HttpSession session = request.getSession(false);
		if(session !=null) {
			session.invalidate(); //세션제거
		}
		return "home";
	}
}
