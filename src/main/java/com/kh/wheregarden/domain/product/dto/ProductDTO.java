package com.kh.wheregarden.domain.product.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

	private int pid;					//상품번호
	private int ppnum;				//식물번호
	private String pname;			//식물명
	private int pprice;				//가격
	private int pstock;				//재고량
	private LocalDate pdate;	//등록일자
	
}
