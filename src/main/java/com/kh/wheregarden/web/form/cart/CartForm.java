package com.kh.wheregarden.web.form.cart;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartForm {

	private int cpid;					//상품번호
	private String cpname; 		//상품명
	private int cprice;				//상품 단일가격
	private int cqty;					//수량
	private int cpsum;				//상품별 총 금액
}
