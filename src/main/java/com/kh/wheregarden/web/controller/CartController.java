package com.kh.wheregarden.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.cart.svc.CartSVC;
import com.kh.wheregarden.web.form.cart.CartForm;
import com.kh.wheregarden.web.form.login.LoginMember;
import com.kh.wheregarden.web.form.order.PlantAndProductForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartSVC cartSVC;
	
  /**
   * 장바구니 등록
   */
  @PostMapping("/myCartAdd")
  public String addCart(
        @ModelAttribute PlantAndProductForm plantAndProductForm,
        Model model,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes) {
     
     HttpSession session = request.getSession(false);
     LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
     
     log.info("입력받은 상품:{}",plantAndProductForm);
     
     //폼에서 전달받은 값 장바구니DTO로 넣기
     CartDTO newCartDTO = new CartDTO(
    		 0, loginMember.getId(), plantAndProductForm.getPid(),
    		 plantAndProductForm.getPNAME(),
    		 plantAndProductForm.getPqty(), plantAndProductForm.getPprice());
     
    // 장바구니 추가시 중복 품목은 수량만 변경
	CartDTO findedCartDTO = cartSVC.findProduct(newCartDTO.getCpid(), loginMember.getId());
	if(findedCartDTO == null) {
		cartSVC.add(newCartDTO);
	}
	else{
		cartSVC.updateQty(newCartDTO);
	}
 
     if(plantAndProductForm.getCartFlag() == 1) {
    	 return "redirect:/cart/myCartList";
     }
     
     Long pnum = plantAndProductForm.getPNUM();
     
     return "redirect:/plant/"+pnum;
  }
	
	/**
	 * 장바구니 조회
	 * @param cnum
	 * @return
	 */
	@GetMapping("/myCartFind")
	public String find(@RequestParam int cnum) {
		CartDTO cartDTO = cartSVC.findCart(cnum);
		log.info("String find() 호출됨");
		
		return cartDTO.toString();
	}
	
  /**
   * 장바구니 목록
   */
  @GetMapping("/myCartList")
  public String list(HttpServletRequest request, Model model) {
     
     HttpSession session = request.getSession(false);
     LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
     
     List<CartDTO> foundCartList = cartSVC.list(loginMember.getId());
     
     log.info("회원아이디로 찾아진 카트리스트:{}", foundCartList);
     model.addAttribute("foundCartList", foundCartList);
     
     return "cart/cartList";
  }

}
