package com.kh.wheregarden.domain.order.svc;

import java.util.List;

import com.kh.wheregarden.domain.order.dto.OrderDTO;
import com.kh.wheregarden.domain.order.dto.OrderDetailDTO;

public interface OrderSVC {

	//주문하기
	public OrderDTO order(OrderDTO orderDTO);
	
	//주문상세조회
	public OrderDTO findOrder(String onum);
	
	//주문내역
	public List<OrderDTO> listOrder(String omid);
	
	//주문취소
	public void deleteOrder(String onum, String omid);
}
