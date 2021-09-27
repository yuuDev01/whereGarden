package com.kh.wheregarden.web.form;

public enum Gender {
	MALE("남자"),FEMALE("여자");

	private final String decode;
	
	Gender(String decode) {
		this.decode = decode;
	}
	
	public String getName() {		//MALE or FEMALE
		return name();
	}
	
	public String getDecode() {		//남자 또는 여자
		return decode;
	}
}
