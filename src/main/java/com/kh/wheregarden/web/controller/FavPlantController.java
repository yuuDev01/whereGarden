package com.kh.wheregarden.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.wheregarden.domain.favPlant.dto.FavDTO;
import com.kh.wheregarden.domain.favPlant.svc.FavSVC;
import com.kh.wheregarden.web.form.login.LoginMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/fav")
public class FavPlantController {
	private final FavSVC favSVC;
	
	@GetMapping("/{pnum}")
	public String addFav(
			@PathVariable Long pnum,
			HttpServletRequest request,Model model) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
			favSVC.addFav(loginMember.getId(), pnum);
			FavDTO favDTO = favSVC.FavDetail(loginMember.getId(), pnum);  //선택한 식물 관심식물 여부
			if(favDTO == null ) {  //만약 받아온 DTO가 null 일 경우 - 관심식물 X
				model.addAttribute("Fav", 0); //0 전달
			}else {  //null이 아닐 경우
				model.addAttribute("Fav", favDTO);
			}
		}
		return "plant/plantList :: #favorite";
	}
}
