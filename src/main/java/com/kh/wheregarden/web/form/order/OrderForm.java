package com.kh.wheregarden.web.form.order;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.kh.wheregarden.domain.order.dto.OrderDetailDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderForm {
	
	public List<OrderDetailDTO> orderDetails;
	private String onum;
	
	@NotEmpty(message="배송정보를 선택하세요")
	private String oinfo;
	private Date odate;
	@NotEmpty(message="이름을 입력하세요")
	private String oname; //	ONAME	VARCHAR2(10 BYTE)
	
	@NotEmpty(message="우편번호를 입력하세요")
	private String opost; //	OPOST	VARCHAR2(10 BYTE)
	@NotEmpty(message="도로명주소를 입력하세요")
	private String oroadname;
	@NotEmpty(message="상세주소를 입력하세요")
	private String oaddress; //	OADDRESS	VARCHAR2(50 BYTE)
	
	
	@NotEmpty(message="전화번호를 입력하세요")
	private String ophone; //	OPHONE	VARCHAR2(20 BYTE)
	private String omemo;//	OMEMO	CLOB
	
	@NotEmpty(message="결제수단을 선택하세요")
	private String opayment; //	OPAYMENT	VARCHAR2(10 BYTE)
	
	private String obank; //	OBANK	VARCHAR2(10 BYTE)
	private String ocardcom; //	OCARDCOM	VARCHAR2(20 BYTE)
	private String ocardnum; //	OCARDNUM	VARCHAR2(20 BYTE)
	private int oprice;			//	OPRICE	NUMBER(10,0)
}
