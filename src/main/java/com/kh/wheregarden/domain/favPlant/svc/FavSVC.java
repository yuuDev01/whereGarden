package com.kh.wheregarden.domain.favPlant.svc;

import java.util.List;

import com.kh.wheregarden.domain.favPlant.dto.FavDTO;
import com.kh.wheregarden.domain.plant.dto.PlantDTO;


public interface FavSVC {
	
	/**
	 * 관심식물 값 찾기
	 * @param mid
	 * @return
	 */
	List<FavDTO> findFav(String mid);
	
	/**
	 * 상세화면 좋아요 값 찾기
	 * @param mid
	 * @param pid
	 * @return
	 */
	FavDTO FavDetail(String mid, Long pid);
	
	
	/**
	 * 관심식물 등록
	 * @param mid
	 * @param pnum
	 */
	void addFav(String mid, Long pnum);
	
	/**
	 * 관심식물 삭제
	 * @param mid
	 * @param pnum
	 */
	void delFav(String mid, Long pnum);
	
	/**
	 * 모든 관심식물
	 * @param mid
	 * @return
	 */
	List<PlantDTO> allFav(String mid);
}
