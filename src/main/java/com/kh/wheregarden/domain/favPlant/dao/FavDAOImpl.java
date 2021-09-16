package com.kh.wheregarden.domain.favPlant.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.board.dao.BoardDAOImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FavDAOImpl implements FavDAO {
	private final JdbcTemplate jt;
	@Override
	public void addFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into fav_plant ");
		sql.append(" values(fav_plant_FPNUM_seq, ?, ?)");
		
		jt.update(sql.toString(), pnum,mid);
		
		
	}

	@Override
	public void delFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete from fav_plant ");
		sql.append("where pnum =? , userid= ? ");
		jt.update(sql.toString(),  pnum, mid);
	}
}
