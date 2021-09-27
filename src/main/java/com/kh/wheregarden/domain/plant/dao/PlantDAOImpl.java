package com.kh.wheregarden.domain.plant.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.dto.PlantTagDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlantDAOImpl implements PlantDAO {
	
	private final JdbcTemplate jt;
	

	// 전체 식물 조회
	@Override
	public List<PlantDTO> list() {
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
		sql.append(" from plant_info  order by pnum");
		List<PlantDTO> list = jt.query(
				sql.toString(),
				new BeanPropertyRowMapper<>(PlantDTO.class));

		return list;
}
	
	
	@Override
	public List<PlantDTO> list(String keyword) {
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
		sql.append(" from plant_info  ");
		sql.append(" where pname like ");
		sql.append(" '%" + keyword + "%' ");
		List<PlantDTO> list = jt.query(
				sql.toString(),
				new BeanPropertyRowMapper<>(PlantDTO.class));

		return list;
	}
	
	@Override
	public List<PlantDTO> list(PlantTagDTO dto) {
		// TODO Auto-generated method stub
		
		
		String light = "%"; //광도
		String manage= "%"; //관리
 		String water ="%";  //물
 		int growth1= 0; int growth2= 220; //높이
		String color ="%";  //색
		String grwhstle ="%";  //생육형태
		
		if(dto.getPLIGHT().equals("") || dto.getPLIGHT() == null) {
			light = "%";
		}else {
			light = dto.getPLIGHT();
		}
		
		
		if(dto.getPMANAGELV().equals("")|| dto.getPMANAGELV() == null) {
			manage = "%";
		}else {
			manage = dto.getPMANAGELV();
		}
		
		
		if(dto.getPWATERSP().equals("")|| dto.getPWATERSP() == null) {
			water = "%";
		}else {
			switch (dto.getPWATERSP()) {
			case "가득":
				water = "%항상 흙을%";
				break;
			case "자주":
				water = "%흙을 촉촉하게%";
				break;
			case "보통":
				water = "%토양 표면이%";
				break;
			case "가끔":
				water = "%화분 흙%";
				break;
			}
		}
		
		
		if(dto.getPGROWTH().equals("")|| dto.getPGROWTH() == null) {
			growth1 = 0; 
		}else {
			switch (dto.getPGROWTH()) {
			case "50 미만":
				growth1 = 0;
				growth2 = 49;
				break;

			case "50-120":
				growth1 = 50;
				growth2 = 120;
				break;
				
			case "120 초과":
				growth1 = 121;
				growth2 = 220;
				break;
			}
			
			
		}
		
		
		
		if(dto.getPFLCOLOR().equals("")|| dto.getPFLCOLOR() == null) {
			color = "%";
		}else {
			color = dto.getPFLCOLOR();
		}
		
		
		
		if(dto.getPGRWHSTLE().equals("") || dto.getPGRWHSTLE() == null) {
			grwhstle = "%";
		}else {
			grwhstle = dto.getPGRWHSTLE();
		}
		
		
		
		
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
		sql.append(" pcount   ");
		sql.append(" from plant_info  ");
		sql.append(" where ");
		sql.append(" plight like '"+ light + "'  ");
		sql.append(" and pmanagelv like '"+ manage + "'");
		sql.append(" and pwatersp like '"+ water + "'");
		sql.append(" and pflcolor like '"+ color + "'");
		sql.append(" and pgrwhstle like '"+ grwhstle + "'");
		sql.append(" and pgrowth between "+ growth1 + " and " +growth2);
		
		
		List<PlantDTO> list =jt.query(
				sql.toString(),
				new BeanPropertyRowMapper<>(PlantDTO.class));
		return list;
	}
	
	
	//식물상세 
	@Override
	public PlantDTO plantDetail(Long pnum) {
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
		sql.append(" pcount   ");
		sql.append(" from plant_info  ");
		sql.append(" where pnum= ? ");
		
		PlantDTO plantDTO = jt.queryForObject(
				sql.toString(),
				new BeanPropertyRowMapper<>(PlantDTO.class),
				pnum
				);
		return plantDTO;
	}
}
