package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

//즉, 인증이 필요없는 곳에는 /auth 를 붙인다.
@Controller
public class UserController {
	
	

	// 회원가입을 하는데 인증이 필요없기 때문에 앞에 /auth를 붙였다.
	@GetMapping("/auth/joinform")
	public String joinForm() {
		
		
		return "user/joinform";
	}
	
	@GetMapping("/auth/loginform")
	public String loginForm() {
		
		
		return "user/loginform";
	}
	
	@GetMapping("/user/updateform")
	public String updateForm() {
		
		
		return "user/updateform";
	}
	
}
