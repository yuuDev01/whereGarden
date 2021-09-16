package com.kh.wheregarden.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
	
	private int cnum; 		//장바구니번호
	private String cmid;  //회원아이디
	private int cpid;		  //상품번호
	private String cname; //상품명
	private int cqty; 		//구매수량
	private int cprice; 	//상품가격
}
