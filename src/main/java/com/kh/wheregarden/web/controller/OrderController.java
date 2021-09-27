package com.kh.wheregarden.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.kh.wheregarden.domain.order.dto.OrderDetailDTO;
import com.kh.wheregarden.domain.order.svc.OrderSVC;
import com.kh.wheregarden.web.form.cart.CartForm;
import com.kh.wheregarden.web.form.login.LoginMember;
import com.kh.wheregarden.web.form.order.OrderForm;
import com.kh.wheregarden.web.form.order.PlantAndProductForm;

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
	
	
	//장바구니 주문처리
	@PostMapping("/form")
	public String cartOrderForm(
			@ModelAttribute OrderForm orderForm,
			BindingResult bindingResult,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		log.info("바로주문에서 받아진 orderForm:{}",orderForm);
		
		for(int i=0; i<orderForm.getOrderDetails().size(); i++) {
			if(orderForm.getOrderDetails().get(i).getOdpname() == null) {
				orderForm.getOrderDetails().remove(i);
			}
		}
		
		log.info("체크된 상품 orderForm:{}",orderForm);
		model.addAttribute("orderForm", orderForm);
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		model.addAttribute("memberDTO",memberDTO);
		
		if(orderForm.orderDetails == null) {
			bindingResult.rejectValue("orderDetails", "orderDetails", "선택된 상품이 없습니다");
			return "order/orderFormPage";
		}
		
		return "order/orderFormPage";
	}
	
	//바로주문 처리
	@PostMapping("/form/express")
	public String orderForm(
			@ModelAttribute PlantAndProductForm plantAndProductForm,
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		log.info("바로주문에서 받아진 plantAndProductForm:{}",plantAndProductForm);
		
		OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
		
		orderDetailDTO.setOdpid(plantAndProductForm.getPid());
		orderDetailDTO.setOdpname(plantAndProductForm.getPNAME());
		orderDetailDTO.setOdqty(plantAndProductForm.getPqty());
		orderDetailDTO.setOdsum(plantAndProductForm.getPqty()*plantAndProductForm.getPprice());
		
		List<OrderDetailDTO> list = new ArrayList<>();
		list.add(orderDetailDTO);
		
		log.info("옮겨준 list:{}",orderDetailDTO);
		
		OrderForm orderForm = new OrderForm();		
		
		orderForm.setOrderDetails(list);
		orderForm.setOprice(plantAndProductForm.getPqty()*plantAndProductForm.getPprice());
		
		log.info("완성된 orderForm:{}",orderForm);
		
		model.addAttribute("orderForm", orderForm);
		
		MemberDTO memberDTO = memberSVC.findMemberById(loginMember.getId());
		
		model.addAttribute("memberDTO",memberDTO);
		
		
		
		return "order/orderFormPage";
	}
	
	//결제처리
	@PostMapping("")
	public String order(
			@ModelAttribute CartForm cartForm,
			@Valid @ModelAttribute OrderForm orderForm,
			BindingResult bindingResult,
			// valid 뒤에 BindingResult 와야함
			HttpServletRequest request,
			Model model) {
		
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");

		//사용자가 입력한 주문정보를 DTO에 저장
		OrderDTO newOrderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderForm, newOrderDTO);
		newOrderDTO.setOmid(loginMember.getId());
		
		log.info("카피된 newOrderDTO:{}", newOrderDTO);
		
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
		
		if(bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
		
			return "order/orderFormPage";
		}
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
