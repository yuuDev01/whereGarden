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
		sql.append("select fpnum, fppnum, fpmid  ");
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
		System.out.println(favDTO);
		return favDTO;	
	}

	// 관심식물 등록
	@Override
	public void addFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("insert into fav_plant ");
		sql.append(" values(fav_plant_FPNUM_seq.nextval, ?, ?)");

		StringBuffer sql2 = new StringBuffer();
		sql2.append("update plant_info ");
		sql2.append("   set pcount = pcount + 1 ");
		sql2.append(" where pnum = ? ");
		

		
		jt.update(sql.toString(), pnum, mid);
		// 식물정보에서 관심식물 count 증가
		jt.update(sql2.toString(), pnum);

	}

	@Override
	public void delFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		StringBuffer sql1 = new StringBuffer();
		sql1.append("delete from fav_plant ");
		sql1.append("where FPPNUM =? and FPMID= ? ");
		jt.update(sql1.toString(), pnum, mid);

		StringBuffer sql2 = new StringBuffer();
		sql2.append("update plant_info ");
		sql2.append("   set pcount = pcount - 1 ");
		sql2.append(" where pnum = ? ");

		// 식물정보에서 관심식물 count 감소
		jt.update(sql2.toString(), pnum);

	}
	
	@Override
	public List<PlantDTO> allFav(String mid) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select  pnum,   ");
		sql.append("  pname,   ");
		sql.append("  pename ,   ");
		sql.append("  pclcode,   ");
		sql.append("  porgplce,   ");
		sql.append("  pfnclty,   ");
		sql.append("  padvise,   ");
		sql.append("  pprpgt,   ");
		sql.append("  plight,   ");
		sql.append("  pmanagelv,   ");
		sql.append("  pflcolor,   ");
		sql.append("  pgrwhstle,   ");
		sql.append("  pgrowth,   ");
		sql.append("  pwatersp,   ");
		sql.append("  pwatersu,   ");
		sql.append("  pwatera,   ");
		sql.append("  pwaterw,   ");
		sql.append("  pplace,   ");
		sql.append("  plthts,   ");
		sql.append("  pspecial,   ");
		sql.append("  pimgurl,   ");
		sql.append("  pcount   ");
		sql.append("from plant_info ");
		sql.append("where pnum in (  ");
		sql.append("  select FPPNUM  ");
		sql.append("  from fav_plant  ");
		sql.append("  where FPMID= ? ) ");
		List<PlantDTO> list = jt.query(
				sql.toString(),
				new BeanPropertyRowMapper<>(PlantDTO.class), mid);
		return list;
	}
}
