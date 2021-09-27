package com.kh.wheregarden.web.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.wheregarden.web.api.JsonResult;
import com.kh.wheregarden.web.apiController.APIMemberController;

@RestControllerAdvice(assignableTypes = APIMemberController.class) //@ControllerAdvice +@ResponseBody
public class ApiGlobalExceptionHandler {

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public JsonResult<String> emptyExHeldler(EmptyResultDataAccessException ex){
		return new JsonResult<String>("01","nok","일치하는 회원정보가 없습니다.");
	}
}
