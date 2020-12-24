package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	// IllegalArgumentException에 해당하는 에러만 받는다.
	//@ExceptionHandler(value= IllegalArgumentException.class)
	@ExceptionHandler(value= Exception.class)
	public String handleArgumentException(IllegalArgumentException e) {
		
		return "<h1>"+e.getMessage()+"</h1>";
	}
	
	
}
