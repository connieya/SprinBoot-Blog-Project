package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
// 서비스가 필요한 이유
// 1. 트랜잭션 관리 2. 서비스 의미 때문
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	public void 회원가입(User user) {
		
		System.out.println("회원가입 성공?");
		
		String rawPassword = user.getPassword(); // ex) 비번 1234
		String encPassword = encoder.encode(rawPassword);
		
		user.setPassword(encPassword);
		
		user.setRole(RoleType.USER);
		System.out.println("userRepository로 DB에 넣자~ 값은 :" +user);
		userRepository.save(user);
		System.out.println("회원가입 성공!!");
		
		//@Transacional 어노테이션이 있는데
		// userRepository.save(user)를 해줘서 에러가 난것이었다.
	}
	
	// Select할 때 트랜잭션 시작, 서비스 종료시에는 트랜잭션 종료(정합성 유지)
	//시큐리티로 로그인을 할거라서 사용안함
	/*
	 * @Transactional(readOnly =true) public User 로그인(User user) {
	 * 
	 * return userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword());
	 * 
	 * }
	 */

}
