package com.kh.wheregarden.domain.order.dao;

import java.util.List;

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.order.dto.OrderDTO;

public interface OrderDAO {

	//주문하기
	public OrderDTO order(OrderDTO orderDTO);
	
	//주문상세조회
	public OrderDTO findOrder(String onum);
	
	//내 주문내역 목록
	public List<OrderDTO> listOrder(String omid);
	
	//주문취소
	public void deleteOrder(String onum, String omid);
	
}
