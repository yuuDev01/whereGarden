package com.kh.wheregarden.domain.order.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.order.dao.OrderDAO;
import com.kh.wheregarden.domain.order.dto.OrderDTO;
import com.kh.wheregarden.domain.order.dto.OrderDetailDTO;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderSVCImpl implements OrderSVC {

	private final OrderDAO orderDAO;
	
	//주문하기
	@Override
	public OrderDTO order(OrderDTO orderDTO) {
		return orderDAO.order(orderDTO);
	}
	
	//주문상세조회
	@Override
	public OrderDTO findOrder(String onum) {
		return orderDAO.findOrder(onum);
	}

	//주문내역
	@Override
	public List<OrderDTO> listOrder(String omid) {
		return orderDAO.listOrder(omid);
	}

	//주문취소
	@Override
	public void deleteOrder(String onum, String omid) {
		orderDAO.deleteOrder(onum, omid);
		
	}

}
