package com.kh.wheregarden.domain.cart.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.cart.dao.CartDAO;
import com.kh.wheregarden.domain.cart.dto.CartDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartSVCImpl implements CartSVC {

	private final CartDAO cartDAO;
	
	/**
	 * 장바구니 등록
	 */
	@Override
	public void add(CartDTO cartDTO) {
		cartDAO.add(cartDTO);
	}
	
	/**
	 * 장바구니 번호조회
	 */
	@Override
	public CartDTO findCart(int cnum) {
		return cartDAO.findCart(cnum);
	}
	
	/**
	 * 장바구니 상품조회
	 */
  @Override
  public CartDTO findProduct(int cpid, String cmid) {
  	return cartDAO.findProduct(cpid, cmid);
  }
	
	/**
	 * 장바구니 목록
	 */
	@Override
	public List<CartDTO> list(String cmid) {
		return cartDAO.list(cmid);
	}

	/**
	 * 장바구니 수량수정
	 */
	@Override
	public void editQty(CartDTO cartDTO) {
		cartDAO.editQty(cartDTO);
	}

	/**
	 * 장바구니 삭제
	 */
	@Override
	public void delete(String cnum) {
		cartDAO.delete(cnum);
	}
	
	/**
	 * 상품명으로 장바구니 삭제
	 */
	@Override
	public void deleteByCname(String cname) {
		cartDAO.deleteByCname(cname);
		
	}
	
	//장바구니 등록시 중복된 상품 수량만 변경
	@Override
	public void updateQty(CartDTO CartDTO) {
		cartDAO.updateQty(CartDTO);
	}


}
