package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //파일을 리턴
public class TempControllerTest {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		
		System.out.println("tempHome()");
		
		//파일 리턴 기본 경로 : src/main/resources/static
		//리턴할때 : /home.html(앞에 / 붙여야함)
		//풀 경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/aa.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix: /WEB-INF/views/
		//suffix :.jsp
		//풀네임: /WEB-INF/views/test.jsp
		
		return "test";
	}
}
