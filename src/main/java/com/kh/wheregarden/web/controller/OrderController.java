package com.kh.wheregarden.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.cart.svc.CartSVC;
import com.kh.wheregarden.domain.member.dto.MemberDTO;
import com.kh.wheregarden.domain.member.svc.MemberSVC;
import com.kh.wheregarden.domain.order.dto.OrderDTO;
import com.kh.wheregarden.domain.order.svc.OrderSVC;
import com.kh.wheregarden.web.form.cart.CartForm;
import com.kh.wheregarden.web.form.login.LoginMember;
import com.kh.wheregarden.web.form.order.OrderForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	
	//서비스 객체 생성
	private final CartSVC cartSVC;
	private final OrderSVC orderSVC;
	private final MemberSVC memberSVC;
	
	
	//장바구니 주문양식
	@GetMapping("/form")
	public String cartOrderForm(
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		
		model.addAttribute("memberDTO",memberDTO);
		
		List<CartDTO> cartDTOList = cartSVC.list(loginMember.getId());
		model.addAttribute("cartDTOList",cartDTOList);
		OrderForm orderForm = new OrderForm();
		model.addAttribute("orderForm", orderForm);
		log.info("주문 양식 호출됨");
		return "order/orderFormPage";
	}
	
	//바로주문
	@PostMapping("/form/express")
	public String orderForm(@ModelAttribute CartForm cartForm, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		log.info("바로주문에서 받아진 cartForm:",cartForm);
		model.addAttribute("cartDTOList", cartForm);
		OrderForm orderForm = new OrderForm();
		model.addAttribute("orderForm", orderForm);
		log.info("바로주문 페이지 호출됨");
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		
		model.addAttribute("memberDTO",memberDTO);
		
		return "order/orderFormPage";
	}
	
	//주문처리
	@PostMapping("")
	public String order(
			@ModelAttribute OrderForm orderForm,
			@ModelAttribute CartForm cartForm,
			BindingResult bindingResult,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		//세션이 없으면 로그인페이지로 이동
		
		if(bindingResult.hasErrors()) {
			return "order/orderFormPage";
		}
		
		log.info("사용자가 입력한 orderForm:{}", orderForm);
		
		//사용자가 입력한 주문정보를 DTO에 저장
		OrderDTO newOrderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderForm, newOrderDTO);
		newOrderDTO.setOmid(loginMember.getId());
//		newOrderDTO.setOrderDetails(cartForm.getCname(),cartForm.getCqty());
		
		log.info("카피된 newOrderDTO:{}", newOrderDTO);
		
//		log.info("orderForm 0번째 상품명:{}", orderForm.getOrderDetails().get(0).getOdpname());
//		log.info("orderForm 0번째 수량:{}", orderForm.getOrderDetails().get(0).getOdqty());
//		log.info("orderForm 1번째 상품명:{}", orderForm.getOrderDetails().get(1).getOdpname());
//		log.info("orderForm 1번째 수량:{}", orderForm.getOrderDetails().get(1).getOdqty());
//		로그 찍을때 인덱스 주의해서 적기. 해당 인덱스 없으면 오류남.
		
		//주문 처리
		OrderDTO orderedDTO = orderSVC.order(newOrderDTO);
		log.info("주문처리 후 orderedDTO:{}",orderedDTO);
		
		//장바구니에서 삭제
		for(int i=0; i<orderedDTO.getOrderDetails().size(); i++) {
			cartSVC.deleteByCname(orderedDTO.getOrderDetails().get(i).getOdpname());
		}
		
		model.addAttribute("orderDTO", orderedDTO);
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		model.addAttribute("memberDTO",memberDTO);
		
		return "order/orderNotice";
	}
	
	//내 주문내역
	@GetMapping("/myOrderList")
	public String orderList(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		
		List<OrderDTO> foundList = orderSVC.listOrder(loginMember.getId());
		
		model.addAttribute("orderList", foundList);
//		List<OrderDetailDTO> orderDetailList = null;
		
//		for(int i=0; i<foundList.get(i).getOrderDetails().size(); i++) {
//			orderDetailList = foundList.get(i).getOrderDetails();
//		}
//		
//
//		model.addAttribute("orderDetailList", orderDetailList);
//		
//		log.info("내 주문내역 호출됨");
//		log.info("foundList:{}",foundList);
		
		return "order/orderList";
	}
	
	//내 주문상세내역
	@GetMapping("/orderDetail/{onum}")
	public String orderDetail(@PathVariable String onum, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		model.addAttribute("memberDTO",memberDTO);
		
		OrderDTO foundOrderDTO = orderSVC.findOrder(onum);
		model.addAttribute("foundOrder", foundOrderDTO);
		
		return "order/orderDetail";
	}
	
	//주문취소
	@GetMapping("/cancel/{onum}/{omid}")
	public String cancel(@PathVariable String onum, @PathVariable String omid, @ModelAttribute OrderForm orderForm, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		model.addAttribute("memberDTO",memberDTO);
		
		orderSVC.deleteOrder(onum, omid);
		
		//주문번호로 주문 찾아오기
		OrderDTO canceledOrderDTO = orderSVC.findOrder(onum);
		
		model.addAttribute("canceledOrderDTO", canceledOrderDTO);
		
		return "order/orderCancel";
	}
}
