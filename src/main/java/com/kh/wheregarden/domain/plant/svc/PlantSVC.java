package com.kh.wheregarden.domain.plant.svc;

import java.util.List;

import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.dto.PlantTagDTO;

public interface PlantSVC {

	/**
	 * 식물전체
	 * @return
	 */
	List<PlantDTO> list();
	
	/**
	 * 검색어 유무
	 * @param keyword
	 * @return
	 */
	List<PlantDTO> list(String keyword);
	
	/**
	 * 태그 선택
	 * @param dto
	 * @return
	 */
	List<PlantDTO> list(PlantTagDTO dto);
	
	/**
	 * 식물 상세
	 * @param pnum
	 * @return
	 */
	PlantDTO plantDetail(Long pnum);
}
