package com.kh.wheregarden.domain.favPlant.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.favPlant.dao.FavDAO;
import com.kh.wheregarden.domain.favPlant.dto.FavDTO;
import com.kh.wheregarden.domain.plant.dao.PlantDAO;
import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.svc.PlantSVCImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavSVCImpl implements FavSVC {
	private final FavDAO favDAO;

	@Override
	public List<PlantDTO> list() {
		// TODO Auto-generated method stub
		return favDAO.list();
	}

	@Override
	public List<FavDTO> findFav(String mid) {
		// TODO Auto-generated method stub
		List<FavDTO> favDTO = favDAO.findFav(mid);
		return favDTO;
	}

	@Override
	public FavDTO FavDetail(String mid, Long pid) {
		// TODO Auto-generated method stub
		FavDTO favDTO = favDAO.FavDetail(mid, pid);
		return favDTO;
	}

	@Override
	public void addFav(String mid, Long pnum) {
		favDAO.addFav(mid, pnum);
	}

	@Override
	public void delFav(String mid, Long pnum) {
		// TODO Auto-generated method stub
		favDAO.delFav(mid, pnum);
	}

	// 전체식물
	@Override
	public List<PlantDTO> allFav(String mid) {
		// TODO Auto-generated method stub
		return favDAO.allFav(mid);
	}
}
