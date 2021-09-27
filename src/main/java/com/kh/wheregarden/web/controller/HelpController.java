package com.kh.wheregarden.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.web.api.FindEmailReq;
import com.kh.wheregarden.web.api.FindPwReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/help")
@RequiredArgsConstructor
public class HelpController {

	private final MemberSVC memberSVC;
	
	@GetMapping("/")
	public String help() {
		return "help/help";
	}
	// 회원 아이디 찾기
	@GetMapping("/findId")
	public String findId(Model model) {
		
		model.addAttribute("findEmailReq", new FindEmailReq());
		return "help/findIdForm";
	}
	//회원 아이디 찾기2
	@GetMapping("/findId2")
	public String findIdForm2(@ModelAttribute FindEmailReq findEmailReq) {
		memberSVC.findEmail(findEmailReq.getName(), findEmailReq.getTel());
		return "help/findIdForm2";
	}
	
	@PostMapping("/findId2")
	public String findId2(@ModelAttribute FindEmailReq findEmailReq) {
		memberSVC.findEmail(findEmailReq.getName(), findEmailReq.getTel());
		return "help/findIdForm2";
	}
	
	//비밀번호 찾기
	@GetMapping("/findPw")
	public String findPwForm(Model model) {
		model.addAttribute("findPwReq", new FindPwReq());
		return "help/findPwForm";
	}
}
