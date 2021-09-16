package com.kh.wheregarden.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.wheregarden.domain.product.dto.ProductDTO;
import com.kh.wheregarden.domain.product.svc.ProductSVC;
import com.kh.wheregarden.web.form.cart.CartForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductSVC productSVC;
	
	//상품목록
	@GetMapping("/productList")
	public String list(Model model) {
		List<ProductDTO> list = productSVC.list();
		model.addAttribute("pList", list);
		log.info("상품 목록 호출됨!");
		return "product/productList";
	}
	
//	//상품조회
//	@GetMapping("/productDetail/{pid}")
//	public String find(@PathVariable int pid, Model model) {
//		ProductDTO productDTO = productSVC.findProduct(pid);
//		model.addAttribute("pDetail", productDTO);
//		return "product/productDetail";
//	}
	
	//상품조회
	@GetMapping("/productDetail/{pid}")
	public String find(@PathVariable int pid, Model model) {
		ProductDTO findedProductDTO = productSVC.findProduct(pid);
		CartForm cartForm = new CartForm(
				findedProductDTO.getPid(), 
				findedProductDTO.getPpnum(), 
				findedProductDTO.getPname(), 
				1,
				findedProductDTO.getPprice(),
				findedProductDTO.getPstock(), 
				findedProductDTO.getPdate(),
				0);
		//pDetail 대신 cartForm 으로 연결하기

		log.info("cartForm:{}", cartForm);
		model.addAttribute("cartForm", cartForm);
		return "product/productDetail";
	}
}
