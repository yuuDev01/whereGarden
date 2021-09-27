package com.kh.wheregarden.domain.comments.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsDTO {
	
    private Long cnum;            	//NUMBER(10),
    private Long cbnum;          	//NUMBER(10),
    private String cid;         	//varchar2(50),
    private String cnickname;   	//varchar2(30),
    private String ccontent;     	//CLOB,
    private Long cpnum;           	//NUMBER(10),
    private Long cgroup;          	//NUMBER(10),
    private LocalDateTime ccdate;	//DATE default sysdate not null,
    private LocalDateTime cudate;	//DATE,
}
