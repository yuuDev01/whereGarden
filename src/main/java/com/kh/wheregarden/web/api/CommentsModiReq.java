package com.kh.wheregarden.web.api;

import lombok.Data;

@Data
public class CommentsModiReq {

	private String id;							//작성한 회원아이디
	private Long cnum;						//댓글 고유번호
	private String modifiedContent;	//수정한 내용
}