package com.kh.wheregarden.domain.product.dao;

import java.util.List;

import com.kh.wheregarden.domain.product.dto.ProductDTO;

public interface ProductDAO {

	//상품 목록
	List<ProductDTO> list();
	
	//상품 조회
	ProductDTO findProduct(int pid);
	
	//상품 조회 by 식물번호
	ProductDTO findProductByPnum(Long pnum);
}
