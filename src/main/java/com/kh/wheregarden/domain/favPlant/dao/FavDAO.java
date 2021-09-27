package com.kh.wheregarden.domain.favPlant.dao;

import java.util.List;

import com.kh.wheregarden.domain.favPlant.dto.FavDTO;

public interface FavDAO {
	
	/**
	 * 관심식물 식물목록
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
	 * 좋아요추가
	 * @param mid
	 * @param pnum
	 */
	void addFav(String mid, Long pnum);
	
	/**
	 * 좋아요 취소
	 * @param mid
	 * @param pnum
	 */
	void delFav(String mid, Long pnum);

}
