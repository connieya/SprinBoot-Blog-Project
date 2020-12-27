package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
///	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	
//	@Autowired
//	private HttpSession session;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		//Username , password, email
		
		System.out.println("save 호출 됨");
		//실제로 DB에 insert하고 아래에서 return이 되면 됨
		System.out.println("UserApiController - post -> service ㄱㄱ");
		System.out.println("user값 넘겨주자 "+user);
		 userService.회원가입(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		System.out.println("회원수정 요청 User 값:"+user);
		System.out.println("서비스 로 ㄱㄱ");
		
		userService.회원수정(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
		
		
	}

	
	//전통적인 방식의 로그인 방법
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user ){
//		System.out.println("login 호출 됨 ");
//		User principal = userService.로그인(user);
//		//principal (접근주체)
//		if(principal != null) {
//			session.setAttribute("principal", principal); //세션 만듬
//			
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}
}
