package com.kh.portfolio.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.portfolio.domain.board.dao.BoardDAO;
import com.kh.portfolio.domain.board.svc.BoardSVC;
import com.kh.portfolio.domain.common.file.FileStore;
import com.kh.portfolio.domain.home.dao.HomeDAO;
import com.kh.portfolio.domain.home.dto.EffectOfColorDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class HomeController {
	
	private final HomeDAO homeDAO;
	
	@RequestMapping("/")
	public String home(Model model) {
		
//		List<EffectOfColorDTO> effectOfColorDTOList = homeDAO.getColorInfo();
//		
//		model.addAttribute("effectOfColorDTOList", effectOfColorDTOList);
		
		return "home";
	}
}