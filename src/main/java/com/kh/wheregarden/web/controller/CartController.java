package com.kh.wheregarden.web.controller;

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

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.cart.svc.CartSVC;
import com.kh.wheregarden.web.form.cart.CartForm;
import com.kh.wheregarden.web.form.login.LoginMember;

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
	public String add(@ModelAttribute CartForm cartForm, HttpServletRequest request) {
		log.info("장바구니 등록 호출됨");
		log.info("컨트롤러에서 받아진 CartForm:{}", cartForm);
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
//		
//		//세션이 없으면 로그인페이지로 이동
//		if(loginMember == null) {
//			return "member/loginPage";
//		}
		
		//정상로직
		CartDTO newCartDTO = new CartDTO(
				0,loginMember.getId(), cartForm.getCpid(), cartForm.getCname(), cartForm.getCqty(), cartForm.getCprice()
		);
		
		CartDTO findedCartDTO = cartSVC.findProduct(cartForm.getCpid());
		log.info("찾아진 findedCartDTO:{}", findedCartDTO);
		if(findedCartDTO==null) {
			log.info("findedCartDTO 널일때:{}", findedCartDTO);
			cartSVC.add(newCartDTO);
		}
		else {
			log.info("newCartDTO 널 아닐때:{}", newCartDTO);
			cartSVC.updateQty(newCartDTO);
		}

		return "/product/productDetail";
		//HttpSession session = request.getSession(false);
		//LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		//세션이 없으면 로그인페이지로 이동
		//if(session == null) return "index";
		
		//cartDTO.setCmid(loginMember.getId());
//		CartDTO newCartDTO = new CartDTO(cartForm);
//		cartSVC.add(cartDTO);
//		return "/product/productDetail";
//		model.addAttribute("cartDTO", storedCartDTO);
//		redirectAttributes.addAttribute("cmid", storedCartDTO.getCmid());
//		return "redirect:/myCartList/{cmid}";
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
		
//		HttpSession session = request.getSession(false);
//		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		//세션이 없으면 로그인페이지로 이동
//			if(loginMember == null) {
//				return "member/loginPage";
//			}
		List<CartDTO> foundList = cartSVC.list("test@test.com");
			
		model.addAttribute("cartList", foundList);
		log.info("내 장바구니 목록 호출됨");
		log.info("cartList:{}",foundList);
		
		return "cart/cartList";
	}
	
	/**
	 * 장바구니 수정
	 * @param cmid
	 * @param model
	 * @return
	 */
//	@GetMapping("/myCartEdit/{cmid}")
//	public String editForm(@PathVariable("cmid") String cmid, Model model) {
//		CartDTO cartDTO = cartSVC.findCart(cmid);
//		model.addAttribute("cartDTO", cartDTO);
//		log.info("String editForm() 호출됨");
//		
//		return "cart/cartEdit";
//	}
	
	/**
	 * 장바구니 삭제
	 * @param cnum
	 * @return
	 */
//	@GetMapping("/mycartDel/{cnum}")
//	public String delete(@PathVariable String cnum) {
//		cartSVC.delete(cnum);
//		log.info("String delete() 호출됨");
//		
//		return "/cart/cartList";
//	}
}
