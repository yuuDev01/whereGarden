package com.kh.wheregarden.domain.product.svc;

import java.util.List;

import com.kh.wheregarden.domain.product.dto.ProductDTO;

public interface ProductSVC {

	//상품 목록
	List<ProductDTO> list();
	
	//상품 조회
	ProductDTO findProduct(int pid);
}
