package com.kh.wheregarden.web.form.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentWriteForm {

	private String cid;			//작성자 아이디
	private String cnickname;	//작성자 닉네임
	private String ccontent;	//댓글 내용
}
