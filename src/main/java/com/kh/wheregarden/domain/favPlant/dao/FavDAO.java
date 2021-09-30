package com.kh.wheregarden.domain.favPlant.dao;

import java.util.List;

import com.kh.wheregarden.domain.favPlant.dto.FavDTO;
import com.kh.wheregarden.domain.plant.dto.PlantDTO;

public interface FavDAO {
	
	/**
	 * 상위 8개 가져오기
	 * @return
	 */
	List<PlantDTO> list();
	
	/**
	 * 관심식물 식물 목록
	 * @param mid
	 * @return
	 */
	List<FavDTO> findFav(String mid);
	

	/**
	 * 관심식물 식물여부
	 * @param mid
	 * @param pid
	 * @return
	 */
	FavDTO FavDetail(String mid, Long pid);
	
	/**
	 *  관심식물 추가
	 * @param mid
	 * @param pnum
	 */
	void addFav(String mid, Long pnum);
	
	/**
	 * 관심식물 취소
	 * @param mid
	 * @param pnum
	 */
	void delFav(String mid, Long pnum);
	
	/**
	 * 관심식물 리스트 plantDTO
	 * @param mid
	 * @return
	 */
	List<PlantDTO> allFav(String mid);
}
