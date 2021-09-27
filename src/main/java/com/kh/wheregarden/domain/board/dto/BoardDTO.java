package com.kh.wheregarden.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.wheregarden.domain.common.dto.UpLoadFileDTO;

import lombok.Data;

@Data
public class BoardDTO {
	  
		private Long bnum;								//	BNUM	NUMBER(10,0)
		private String bmid; 			  			//	BMID	VARCHAR2(40 BYTE)
		private String bnickname;					//	BNICKNAME	VARCHAR2(30 BYTE)
		private String bcategory;					//	BCATEGORY	VARCHAR2(11 BYTE)
	  private String btitle; 			  		//	BTITLE	VARCHAR2(150 BYTE)
	  private String bcontent;					//	BCONTENT	CLOB
		private Long bhit;								//	BHIT	NUMBER(5,0)
		private Long bpnum;								//	PBNUM	NUMBER(10,0)
		private Long bgroup;							//	BGROUP	NUMBER(10,0)
		private Long bstep;								//	BSTEP	NUMBER(3,0)
		private Long bindent;							//	BINDENT	NUMBER(3,0)
		//private String status;					//	STATUS	CHAR(1 BYTE)
		private LocalDateTime bcdate;			//	BCDATE	date default sysdate not null,
		private LocalDateTime budate;			//	BUDATE	date
		
		private List<UpLoadFileDTO> files;
}
