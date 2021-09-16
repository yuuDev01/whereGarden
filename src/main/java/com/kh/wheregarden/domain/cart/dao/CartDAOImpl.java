package com.kh.wheregarden.domain.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.cart.dto.CartDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartDAOImpl implements CartDAO {

	private final JdbcTemplate jt;
	
	//장바구니 등록
	@Override
	public void add(CartDTO cartDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into cart(cnum,cmid,cpid,cname,cqty,cprice) ");
		sql.append("values (cart_cnum_seq.nextval,?,?,?,?,?) ");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[] {"cnum"});
				pstmt.setString(1, cartDTO.getCmid());
				pstmt.setInt(2, cartDTO.getCpid());
				pstmt.setString(3, cartDTO.getCname());
				pstmt.setString(4, Integer.toString(cartDTO.getCqty()));
				pstmt.setInt(5, cartDTO.getCprice());
				return pstmt;
			}
		}, keyHolder);
		String key = keyHolder.getKeys().get("cnum").toString();
		log.info("키홀더:{}",key);
		//return findCart(keyHolder.getKeys().get("cnum").toString());
	}
	
	//장바구니 번호조회
	@Override
	public CartDTO findCart(int cnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cnum,cmid,cpid,cname,cqty,cprice ");
		sql.append("from cart ");
		sql.append("where cnum = ? ");
		
		CartDTO cartDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(CartDTO.class),cnum);
		return cartDTO;
	}
	
	//장바구니 상품조회
	@Override
	public CartDTO findProduct(int cpid) {
		CartDTO findedCartDTO = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select cnum,cmid,cpid,cname,cqty,cprice ");
		sql.append("from cart ");
		sql.append("where cpid = ? ");
		
		try {
			findedCartDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(CartDTO.class),cpid);
		}
		catch (EmptyResultDataAccessException e){
			findedCartDTO = null;
		}
		
		log.info("DAO에서 찾아진 findedCartDTO:{}",findedCartDTO);
		return findedCartDTO;
	}
	
	//장바구니 목록
	@Override
	public List<CartDTO> list(String cmid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cnum,cmid,cpid,cname,cqty,cprice  ");
		sql.append("from cart ");
		sql.append("where cmid = ? ");
		
		List<CartDTO> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(CartDTO.class),cmid);
		return list;
	}

	//장바구니 수량 수정
	@Override
	public void editQty(CartDTO cartDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update cart ");
		sql.append("set cqty = ? ");
		sql.append("where cmid = ? ");
		sql.append("and cpid = ? ");
		
		jt.update(sql.toString(),
							cartDTO.getCqty(),
							cartDTO.getCmid(),
							cartDTO.getCpid());
	
	}

	//장바구니 삭제
	@Override
	public void delete(String cnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from cart ");
		sql.append("where cnum = ? ");
		
		jt.update(sql.toString(), cnum);

	}
	
	//장바구니 삭제
	@Override
	public void deleteByCname(String cname) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from cart ");
		sql.append("where cname = ? ");
		
		jt.update(sql.toString(), cname);

	}
	
	//장바구니 등록시 중복된 상품 수량만 변경
	@Override
	public void updateQty(CartDTO cartDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update cart ");
		sql.append("set cqty = cqty + ? ");
		sql.append("where cpid=? ");
		
		
		jt.update(sql.toString(),cartDTO.getCqty() ,cartDTO.getCpid());
		
	}
}
