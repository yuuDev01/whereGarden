package com.kh.wheregarden.web.form.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantAndProductForm {
	private Long PNUM; //  PNUM
  private String PNAME; // PNAME VARCHAR2(80),
  private String PENAME; // PENAME VARCHAR2(128),
  private String PCLCODE;// PCLCODE VARCHAR2(80),분류
  private String PORGPLCE;// PORGPLCE VARCHAR2(128),원산지
  private String PFNCLTY; // PFNCLTY VARCHAR2(4000),
  private String PADVISE; // PADVISE VARCHAR2(500),
  private String PPRPGT; // PPRPGT VARCHAR2(80),
  private String PLIGHT; // PLIGHT VARCHAR2(40), 광도
  private String PMANAGELV; // PMANAGELV VARCHAR2(40),관리요구도
  private String PFLCOLOR; // PFLCOLOR VARCHAR2(40), 꽃색
  private String PGRWHSTLE; // PGRWHSTLE VARCHAR2(40),
  private Long PGROWTH; // PGROWTH NUMBER(38), 성장높이
  private String PWATERSP; //PWATERSP VARCHAR2(26), 봄 물주기
  private String PWATERSU; //PWATERSU VARCHAR2(26), 여름
  private String PWATERA; //PWATERA VARCHAR2(26),    가을 
  private String PWATERW;  //PWATERW VARCHAR2(26),  겨울
  private String PPLACE; //PPLACE VARCHAR2(500), 실내공간 위치
  private String PLTHTS; 	//PLTHTS VARCHAR2(80), 병해충해 정보
  private String PSPECIAL;	 // PSPECIAL VARCHAR2(1024), 특별관리정보
  private String PIMGURL;	 // PIMGURL VARCHAR2(128) 이미지 링크
  private Long PCOUNT;		 // PCOUNT NUMBER(38))  관심식물수
	
	private int pid;					//상품번호
	private int pprice;				//가격
	private int pqty;				//주문할 수량
	
	private int cartFlag;		//장바구니로 옮길지 말지에 대한 플래그
}