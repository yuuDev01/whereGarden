package com.kh.wheregarden.domain.favPlant.dao;

public interface FavDAO {
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
