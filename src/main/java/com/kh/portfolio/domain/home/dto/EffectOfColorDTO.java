package com.kh.portfolio.domain.home.dto;

import lombok.Data;

@Data
public class EffectOfColorDTO {

	private String color;		//color VARCHAR2(26),
	private String effect;		//effect VARCHAR2(128),
	private String season;		//erainfo VARCHAR2(80),
	private String spaceinfo;		//spaceinfo VARCHAR2(80),
	private String imgUrl;		//imgUrl VARCHAR2(128));
}
