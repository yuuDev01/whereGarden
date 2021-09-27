package com.kh.wheregarden.domain.product.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.product.dao.ProductDAO;
import com.kh.wheregarden.domain.product.dto.ProductDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC {

	private final ProductDAO productDAO;
	
	//상품 목록
	@Override
	public List<ProductDTO> list() {
		return productDAO.list();
	}

	//상품 조회
	@Override
	public ProductDTO findProduct(int pid) {
		return productDAO.findProduct(pid);
	}

 //상품 조회 by 식물번호
	@Override
	public ProductDTO findProductByPnum(Long pnum) {
		return productDAO.findProductByPnum(pnum);
	}
}
