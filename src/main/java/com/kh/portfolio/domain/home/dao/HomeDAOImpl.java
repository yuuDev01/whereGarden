package com.kh.portfolio.domain.home.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.portfolio.domain.board.dao.BoardDAOImpl;
import com.kh.portfolio.domain.board.dto.BoardDTO;
import com.kh.portfolio.domain.home.dto.EffectOfColorDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HomeDAOImpl implements HomeDAO {
	
	private final JdbcTemplate jt;

	@Override
	public List<EffectOfColorDTO> getColorInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from PLANT_COLOR ");
		
		List<EffectOfColorDTO> effectOfColorDTOList =
				jt.query(sql.toString(), new BeanPropertyRowMapper<>(EffectOfColorDTO.class));
		
		return effectOfColorDTOList;
	}
	
}
