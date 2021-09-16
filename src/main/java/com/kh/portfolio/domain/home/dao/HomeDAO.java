package com.kh.portfolio.domain.home.dao;

import java.util.List;

import com.kh.portfolio.domain.home.dto.EffectOfColorDTO;

public interface HomeDAO {

	//꽃 효능 찾아오기
	List<EffectOfColorDTO> getColorInfo();
}
