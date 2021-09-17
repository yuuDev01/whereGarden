package com.kh.wheregarden.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

	private String odid;		//주문상세아이디
	private String odonum;	//주문을 참조하는 주문번호
	private int odpid;			//주문상세 상품번호
	private String odpname;	//주문상세 상품명
	private int odqty;			//주문상세 상품수량
	private int odsum;			//주문하는 하나의 품목 총 금액
}