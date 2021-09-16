package com.kh.wheregarden.domain.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.cart.dto.CartDTO;
import com.kh.wheregarden.domain.order.dto.OrderDTO;
import com.kh.wheregarden.domain.order.dto.OrderDetailDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {

	private final JdbcTemplate jt;
	
	/**
	 * 주문하기
	 */
	@Override
	public OrderDTO order(OrderDTO orderDTO) {
		log.info("DAO orderDTO:{}", orderDTO);
		StringBuffer sql1 = new StringBuffer();
		sql1.append("insert into orders ( ");
		sql1.append("  onum, ");
		sql1.append("  omid, ");
		sql1.append("  oname, ");
		sql1.append("  opost, ");
		sql1.append("  oroadname, ");
		sql1.append("  oaddress, ");
		sql1.append("  ophone, ");
		sql1.append("  omemo, ");
		sql1.append("  opayment, ");
		sql1.append("  obank, ");
		sql1.append("  ocardcom, ");
		sql1.append("  ocardnum, ");
		sql1.append("  oprice, ");
		sql1.append("  oresign, ");
		sql1.append("  rdate ");
		sql1.append(") values ( ");
		sql1.append("  orders_onum_seq.nextval, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  ?, ");
		sql1.append("  null ");
		sql1.append(") ");
		
		KeyHolder keyHolder1 = new GeneratedKeyHolder();
		
		jt.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql1.toString(), new String[] {"onum"});
				pstmt.setString(1, orderDTO.getOmid());
				pstmt.setString(2, orderDTO.getOname());
				pstmt.setString(3, orderDTO.getOpost());
				pstmt.setString(4, orderDTO.getOroadname());
				pstmt.setString(5, orderDTO.getOaddress());
				pstmt.setString(6, orderDTO.getOphone());
				pstmt.setString(7, orderDTO.getOmemo());
				pstmt.setString(8, orderDTO.getOpayment());
				pstmt.setString(9, orderDTO.getObank());
				pstmt.setString(10, orderDTO.getOcardcom());
				pstmt.setString(11, orderDTO.getOcardnum());
				pstmt.setInt(12, orderDTO.getOprice());
				pstmt.setInt(13, 0);
				return pstmt;
			}
		}, keyHolder1);
		String key1 = keyHolder1.getKeys().get("onum").toString();
		
		StringBuffer sql2 = new StringBuffer();
			sql2.append("insert into orders_detail(odid,odonum,odpid,odpname,odqty,odsum) ");
			sql2.append("values (od_odid_seq.nextval, orders_onum_seq.currval ,?,?,?,?) ");
		
		jt.batchUpdate(sql2.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {	
					ps.setString(1, orderDTO.getOrderDetails().get(i).getOdpid());
					ps.setString(2, orderDTO.getOrderDetails().get(i).getOdpname());
					ps.setInt(3, orderDTO.getOrderDetails().get(i).getOdqty());
					ps.setInt(4, orderDTO.getOrderDetails().get(i).getOdsum());
			}
			
			@Override
			public int getBatchSize() {
				return orderDTO.getOrderDetails().size();
			}
		
		});
		return findOrder(key1);
	}
	
	
	/**
	 * 주문 상세 조회
	 */
	@Override
	public OrderDTO findOrder(String onum) {
		StringBuffer sql1 = new StringBuffer();
		sql1.append("select onum,omid,oname,odate,oaddress,oroadname,opost,ophone,omemo,opayment,obank,ocardcom,oprice,rdate ");
		sql1.append("from orders ");
		sql1.append("where onum = ? ");
		
		OrderDTO orderDTO = jt.queryForObject(sql1.toString(), new BeanPropertyRowMapper<>(OrderDTO.class),onum);
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select * ");
		sql2.append("from orders_detail ");
		sql2.append("where odonum = ? ");
		
		List<OrderDetailDTO> orderDetailDTO = jt.query(sql2.toString(), new BeanPropertyRowMapper<>(OrderDetailDTO.class), onum);
		orderDTO.setOrderDetails(orderDetailDTO);
		
		log.info("findedOrder로 찾은 orderDTO:{}", orderDTO);
		
		return orderDTO;
		
		
	}

	/**
	 * 내 주문내역 조회
	 */
	@Override
	public List<OrderDTO> listOrder(String omid) {
		StringBuffer sql1 = new StringBuffer();
		sql1.append("select onum,omid,odate,oname,oaddress,oroadname,opost,ophone,omemo,opayment,obank,ocardcom,oprice,rdate ");
		sql1.append("from orders ");
		sql1.append("where omid = ? ");
		
		List<OrderDTO> list = jt.query(sql1.toString(), new BeanPropertyRowMapper<>(OrderDTO.class),omid);
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select *  ");
		sql2.append("from orders_detail ");
		sql2.append("where odonum in (select onum  ");
		sql2.append("                 from orders  ");
		sql2.append("                 where omid = ?) ");
		
		List<OrderDetailDTO> orderDetailDTO = jt.query(sql2.toString(), new BeanPropertyRowMapper<>(OrderDetailDTO.class), omid);
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setOrderDetails(orderDetailDTO);
		}
		
		return list;
	}
	
	

	/**
	 * 주문 취소
	 */
	@Override
	public void deleteOrder(String onum, String omid) {
		StringBuffer sql = new StringBuffer();
		sql.append("update orders ");
		sql.append("set oresign = 1, ");
		sql.append("	  rdate = systimestamp ");
		sql.append("where onum = ? ");
		sql.append("and omid = ? ");
		
		jt.update(sql.toString(), onum, omid);
	}

}
