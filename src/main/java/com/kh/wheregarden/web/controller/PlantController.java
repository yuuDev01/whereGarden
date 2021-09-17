package com.kh.wheregarden.web.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.dto.PlantTagDTO;
import com.kh.wheregarden.domain.plant.svc.PlantSVC;
import com.kh.wheregarden.domain.product.dto.ProductDTO;
import com.kh.wheregarden.domain.product.svc.ProductSVC;
import com.kh.wheregarden.web.form.cart.CartForm;
import com.kh.wheregarden.web.form.order.PlantAndProductForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/plant")
public class PlantController {

	private final PlantSVC plantSVC;
	private final ProductSVC productSVC;

	// 전체 식물조회
	@GetMapping("/list")
	public String allList(
			@RequestParam(required = false) String keyword,
			Model model) {
		List<PlantDTO> list = null;
		if(keyword == null || keyword.equals("")) {
			list = plantSVC.list();
		}else {
			list = plantSVC.list(keyword);
		}
		model.addAttribute("plantList", list);
		return "plant/plantList";
	}
	
	
	@RequestMapping(value ="/list/tag", method = RequestMethod.GET)
	public String List(
			PlantTagDTO dto,
			Model model) {
		List<PlantDTO> list = null;
		list = plantSVC.list(dto); //keyword 포함된 글 조회	
		model.addAttribute("plantList", list);
		
		return "plant/plantList :: #plant_view";
	}

 
	
	@GetMapping("/{pnum}")
	public String PlantDetail(
			@PathVariable Long pnum,
			@ModelAttribute PlantAndProductForm plantAndProductForm
			) {
		
		PlantDTO foundPlantDTO = plantSVC.plantDetail(pnum);
		
		BeanUtils.copyProperties(foundPlantDTO, plantAndProductForm);
		log.info("식물정보 복사 plantAndProductForm:{}",plantAndProductForm);
		
		ProductDTO foundProductDTO = productSVC.findProductByPnum(pnum);
		plantAndProductForm.setPid(foundProductDTO.getPid());
		plantAndProductForm.setPprice(foundProductDTO.getPprice());
		plantAndProductForm.setPqty(1);
		
		log.info("상품정보 복사 plantAndProductForm:{}",plantAndProductForm);
		
		return "plant/plantDetail";
	}
}
