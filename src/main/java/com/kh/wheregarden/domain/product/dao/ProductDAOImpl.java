package com.kh.wheregarden.domain.product.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.product.dto.ProductDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

	private final JdbcTemplate jt;
	
	//상품 목록
	@Override
	public List<ProductDTO> list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,ppnum,pname,pprice,pstock,pdate ");
		sql.append(" from product ");
		
		List<ProductDTO> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(ProductDTO.class));
		return list;
		
	}

	//상품 조회
	@Override
	public ProductDTO findProduct(int pid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,ppnum,pname,pprice,pstock,pdate ");
		sql.append(" from product ");
		sql.append(" where pid = ? ");
		System.out.println(sql.toString());
		ProductDTO productDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(ProductDTO.class), pid);
		return productDTO;
	}
	
	@Override
	public ProductDTO findProductByPnum(Long pnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,ppnum,pname,pprice,pstock,pdate ");
		sql.append(" from product ");
		sql.append(" where ppnum = ? ");
		ProductDTO productDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(ProductDTO.class), pnum);
		System.out.println(productDTO.toString());
		return productDTO;
	}

}
