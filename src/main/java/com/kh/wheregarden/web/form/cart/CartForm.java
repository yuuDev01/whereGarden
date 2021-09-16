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
	private int cpnum;				//식물번호
	private String cname; 		//식물명
	private int cqty;					//수량
	private int cprice;				//가격
	private int cpstock;			//재고량
	private LocalDate cpdate;	//등록일
	private int csum;					//합계
}
