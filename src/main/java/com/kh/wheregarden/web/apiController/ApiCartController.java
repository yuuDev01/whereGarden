package com.kh.wheregarden.web.apiController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.cart.svc.CartSVC;
import com.kh.wheregarden.web.api.JsonResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ApiCartController {

	private final CartSVC cartSVC;
	
	//장바구니에서 삭제
	@DeleteMapping("/{cnum}")
	public JsonResult<String> delCartItem(@PathVariable String cnum) {
		cartSVC.delete(cnum);
		return new JsonResult<String>("00","ok",cnum);
	}
	
	//장바구니 수량 수정
	@PatchMapping("/edit")
	public JsonResult<Integer> editCartItem(
			@RequestBody CartDTO cartDTO) {
		cartSVC.editQty(cartDTO);
		CartDTO updatedCartDTO = cartSVC.findCart(cartDTO.getCnum());
		return new JsonResult<Integer>("00","ok", updatedCartDTO.getCqty());
	}
	
	
}
