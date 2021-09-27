package com.kh.wheregarden.domain.favPlant.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.wheregarden.domain.board.dao.BoardDAOImpl;
import com.kh.wheregarden.domain.favPlant.dto.FavDTO;
import com.kh.wheregarden.domain.plant.dto.PlantDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FavDAOImpl implements FavDAO {
	private final JdbcTemplate jt;

	// 회원별 관심식물 목록 찾기
	@Override
	public List<FavDTO> findFav(String mid) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select FPNUM, FPPNUM, FPMID  ");
		sql.append("From FAV_PlANT  ");
		sql.append(" where FPMID = ?");
		List<FavDTO> list = jt.query(sql.toString(), new BeanPropertyRowMapper<>(FavDTO.class), mid);

		return list;
	}

	// 식물상세 - 관심식물 여부
	@Override
	public FavDTO FavDetail(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select FPNUM, FPPNUM, FPMID  ");
		sql.append("from fav_plant  ");
		sql.append(" where FPMID = ? and FPPNUM = ? ");
		FavDTO favDTO = null;

		try {
			if (jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(FavDTO.class), mid, pnum) != null) {
				favDTO = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(FavDTO.class), mid, pnum);
				
			}
		} catch (EmptyResultDataAccessException e) {

			favDTO = null;

		}
		return favDTO;	
	}

	// 관심식물 등록
	@Override
	public void addFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into fav_plant ");
		sql.append(" values(fav_plant_FPNUM_seq, ?, ?)");

		StringBuffer sql2 = new StringBuffer();
		sql.append("update plant_info ");
		sql.append("   set pcount = pcount + 1 ");
		sql.append(" where pnum = ? ");

		jt.update(sql.toString(), pnum, mid);
		// 식물정보에서 관심식물 count 증가
		jt.update(sql2.toString(), pnum);

	}

	@Override
	public void delFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete from fav_plant ");
		sql.append("where pnum =? , userid= ? ");
		jt.update(sql.toString(), pnum, mid);

		StringBuffer sql2 = new StringBuffer();
		sql.append("update plant_info ");
		sql.append("   set pcount = pcount - 1 ");
		sql.append(" where pnum = ? ");

		// 식물정보에서 관심식물 count 감소
		jt.update(sql2.toString(), pnum);

	}
}
