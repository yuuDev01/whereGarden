package com.kh.wheregarden.domain.plant.dao;

import java.util.List;

import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.dto.PlantTagDTO;

public interface PlantDAO  {

	
	/**
	 * 전체 식물 조회
	 * @return
	 */
	List<PlantDTO> list();
	
	
	/**
	 * 검색하여 식물 조회
	 * @param keyword
	 * @return
	 */
	List<PlantDTO> list(String keyword);
	
	
	List<PlantDTO> list(PlantTagDTO dto);
	
	/**
	 * 식물 상세
	 * @param pnum
	 * @return
	 */
	PlantDTO plantDetail(Long pnum);
}
