package com.kh.wheregarden.web.apiController;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.web.api.FindEmailReq;
import com.kh.wheregarden.web.api.FindPwReq;
import com.kh.wheregarden.web.api.JsonResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController  //@Controller + @ResponseBody
@RequestMapping("/api/members")
@AllArgsConstructor
public class APIMemberController {

	private final MemberSVC memberSVC;
	
	//아이디(이메일) 중복체크
	@GetMapping("/email/dupchk")
	public JsonResult<String> dupChkEmail(
			@RequestParam String mid
			){
		
		JsonResult<String> result = null;
		if(memberSVC.isExistEmail(mid)) {
			result = new JsonResult<String>("00", "OK", mid);
		}else {
			result = new JsonResult<String>("01", "NOK", null);
		}
		
		return result;
	}
	

	//이메일 찾기
	@PostMapping("/email")
	public JsonResult<String> findMid(
			@RequestBody FindEmailReq findEmailReq,
			BindingResult bindingResult
			) {

		log.info("findEmailReq:{}",findEmailReq);
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String findedMid = 
				memberSVC.findEmail(findEmailReq.getName(),findEmailReq.getTel());
		
		return new JsonResult<String>("00","ok",findedMid);
	}
	
	//닉네임 중복체크
		@GetMapping("/nickname/dupchk")
		public JsonResult<String> dupChkNickname(
				@RequestParam String mnickname
				){
			
			JsonResult<String> result = null;
			if(memberSVC.isExistNickname(mnickname)) {
				result = new JsonResult<String>("00", "OK", mnickname);
			}else {
				result = new JsonResult<String>("01", "NOK", null);
			}
			
			return result;
		}
		

	
	//비밀번호 찾기
	@PostMapping("/pw")
		public JsonResult<String> findPw(
			@RequestBody FindPwReq findPwReq,
			BindingResult bindingResult
			) {

		log.info("FindPwReq:{}",findPwReq);
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String findedmpw = 
				memberSVC.findPw(
						findPwReq.getMid(),findPwReq.getMquestion(),findPwReq.getManswer());
		
		return new JsonResult<String>("00","ok",findedmpw);
	}
	
	
}
