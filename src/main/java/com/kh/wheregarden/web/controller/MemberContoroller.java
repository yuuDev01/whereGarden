package com.kh.wheregarden.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.wheregarden.domain.member.dto.MemberDTO;
import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.web.form.Gender;
import com.kh.wheregarden.web.form.login.LoginMember;
import com.kh.wheregarden.web.form.member.EditForm;
import com.kh.wheregarden.web.form.member.JoinForm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberContoroller {

	private final MemberSVC memberSVC;
	
	@ModelAttribute("gender")
	public Gender[] gender() {
		System.out.println(Gender.values());
		return Gender.values();
	}
	/**
	 * 회원가입양식
	 * @return
	 */
	@GetMapping("/join")
	public String joinForm(Model model) {
		log.info("회원가입양식 호출됨!");
		model.addAttribute("joinForm", new JoinForm());
		return "members/joinForm";
	}
	/**
	 * 회원가입처리
	 * @return
	 */
	@PostMapping("/join")
	public String join(
			@Valid @ModelAttribute JoinForm joinForm,
			BindingResult bindingResult) {
		log.info("회원가입처리 호출됨!");
		log.info("joinForm:{}",joinForm);
		
		//회원 존재유무
		if(memberSVC.isExistEmail(joinForm.getMid())) {
			bindingResult.rejectValue("mid","mid", "동일한 이메일이 존재합니다");
			return "members/joinForm";
		}
		
		//탈퇴된 회원 유무
		if(memberSVC.deleteEmail(joinForm.getMid())) {
			bindingResult.rejectValue("delmid","delmid", "이미 삭제된 회원의 아이디입니다");
			return "members/joinForm";
		}
		
		//비밀번호 확인 체크
		if(!joinForm.getMpw().equals(joinForm.getMpwchk())) {
			bindingResult.rejectValue("mpwchk", "mpwchk", "비밀번호가 일치하지 않습니다.");
			return "members/joinForm";			
		}
		
		//전화번호 중복유무
		if(memberSVC.isExistTell(joinForm.getMtel())) {
			bindingResult.rejectValue("mtel","mtel", "동일한 전화번호가 존재합니다");
			return "members/joinForm";
		}
		
		//닉네임 존재유무
		if(memberSVC.isExistNickname(joinForm.getMnickname())) {
			bindingResult.rejectValue("mnickname","mnickname", "동일한 닉네임이 존재합니다");
			return "members/joinForm";
		}

		if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "members/joinForm";
		}
		
		MemberDTO mdto = new MemberDTO();
		BeanUtils.copyProperties(joinForm, mdto);
		mdto.setMgender(joinForm.getGender());
		log.info("카피된 빈:{}",mdto);
		memberSVC.join(mdto);
		
		
		return "redirect:/login";
	}
	

	
	/**
	 * 회원수정양식
	 * @return
	 */
	@GetMapping("/edit")
	public String editForm(
			HttpServletRequest request,
			Model model) {
		log.info("회원수정양식 호출됨!");
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
	/**
	 * 회원수정처리
	 * @return
	 */
	@PatchMapping("/edit")
	public String edit(
			@Valid @ModelAttribute EditForm editForm,
			BindingResult bindingResult,
			HttpServletRequest request) {
		log.info("회원수정처리 호출됨");
		HttpSession session = request.getSession(false);
		LoginMember loginMember 
			= (LoginMember)session.getAttribute("loginMember");
		log.info("회원 수정 처리:{}"+loginMember.toString());
		//세션이 없으면 로그인 페이지로 이동
		if(loginMember == null) return "redirect:/login";
		
		//비밀번호를 잘못입력했을경우
		if(!memberSVC.isMeMember(loginMember.getId(), editForm.getMpw())) {
			bindingResult.rejectValue("pw", "error.member.editForm", "비밀번호가 잘못입력되었습니다.");
		}
		
		if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "mypage/memberEditForm";
		}
		
		
		MemberDTO mdto = new MemberDTO();
		BeanUtils.copyProperties(editForm, mdto);

		
		memberSVC.update(loginMember.getId(), mdto);
		log.info("=={},{}",loginMember.getId(), mdto);
		return "redirect:/members/edit";
	}
	/**
	 * 회원조회
	 * @return
	 */
	@GetMapping("/{id:.+}")
	public String view(@PathVariable("id") String id) {
		log.info("회원조회 호출됨");
		log.info("회원:{}",id);
		return "members/view";
	}
	
	/**
	 * 비밀번호 변경
	 * @return
	 */
	@GetMapping("/pw")
	public String changePwForm() {
		
		return "members/changePwForm";
	}
	
	/**
	 * 비밀번호 변경처리
	 * @return
	 */
	@PatchMapping("/pw")
	public String changePw() {
		
		return "redirect:/members/mypage";
	}
	
	
	/**
	 * 회원 탈퇴
	 * @return
	 */
	@GetMapping("/out")
	public String outForm() {
		log.info("회원 탈퇴 양식 호출");
		
		return "members/out";
	}
	/**
	 * 회원탈퇴
	 * @return
	 */
	@DeleteMapping("/{id:.+}")
	public String out(@PathVariable("id") String id) {
		log.info("회원탈퇴");
		log.info("회원:{}",id);
		return "home";
	}
	/**
	 * 회원목록
	 * @return
	 */
	@GetMapping("")
	public String listAll() {
		log.info("회원목록");
		return "members/list";
	}
	
	
	
}
