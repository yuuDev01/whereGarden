package com.kh.wheregarden.domain.cart.dao;

import java.util.List;

import com.kh.wheregarden.domain.cart.dto.CartDTO;

public interface CartDAO {
	//장바구니 등록
	public void add(CartDTO cartDTO);
	
	//장바구니 번호조회
	public CartDTO findCart(int cnum);
	
	//장바구니 상품조회
	public CartDTO findProduct(int cpid, String cmid);
	
	//장바구니 목록
	public List<CartDTO> list(String cmid);
	
	//장바구니 수량 수정
	public void editQty(CartDTO cartDTO);
	
	//장바구니 삭제 by 장바구니번호
	public void delete(String cnum);
	
	//장바구니 삭제 by 상품명
	public void deleteByCname(String cname);
	
	//장바구니 등록시 중복된 상품 수량만 변경
	public void updateQty(CartDTO CartDTO);
}
