package com.kh.wheregarden.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

	private String odid;
	private String odonum;
	private String odpid;
	private String odpname;
	private int odqty;
	private int odsum;
}