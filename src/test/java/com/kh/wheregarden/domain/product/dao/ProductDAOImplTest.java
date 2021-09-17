package com.kh.wheregarden.domain.product.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.wheregarden.domain.product.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProductDAOImplTest {

	@Autowired
	private  ProductDAO pdao;
	
	@Test
	@DisplayName("상품 찾기")
	void findProduct() {
		ProductDTO pdto = pdao.findProductByPnum(26L);
		String findedEmail = 
				pdto.toString();
//		Assertions.assertThat(findedEmail).isEqualTo(mdto.getMid());
	}
}
