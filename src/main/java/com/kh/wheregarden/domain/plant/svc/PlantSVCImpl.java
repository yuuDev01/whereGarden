package com.kh.wheregarden.domain.plant.svc;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.wheregarden.domain.board.dao.BoardDAO;
import com.kh.wheregarden.domain.board.svc.BoardSVCImpl;
import com.kh.wheregarden.domain.common.dao.UpLoadFileDAO;
import com.kh.wheregarden.domain.common.file.FileStore;
import com.kh.wheregarden.domain.plant.dao.PlantDAO;
import com.kh.wheregarden.domain.plant.dto.PlantDTO;
import com.kh.wheregarden.domain.plant.dto.PlantTagDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantSVCImpl implements PlantSVC{
	private final PlantDAO plantDAO;
	

	//전체 식물
	@Override
	public List<PlantDTO> list() {
		List<PlantDTO> plantList = plantDAO.list();
		return plantList;
	}
	
	
	//검색 식물
	@Override
	public List<PlantDTO> list(String keyword) {
		List<PlantDTO> plantList = plantDAO.list(keyword);
		return plantList;
	}
	
	
	//태그선택 식물
	@Override
	public List<PlantDTO> list(PlantTagDTO dto) {
		// TODO Auto-generated method stub
		List<PlantDTO> plantList = plantDAO.list(dto);
		
		return plantList;
	}
	
	
	//식물상세
	@Override
	public PlantDTO plantDetail(Long pnum) {
		// TODO Auto-generated method stub
		PlantDTO plantDTO = plantDAO.plantDetail(pnum);
		return plantDTO;
	}
}
