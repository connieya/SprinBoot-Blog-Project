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
	@Transactional
	public void 회원수정(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고,
		//영속화 된 User 오브젝트를 수정
		//select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		User persistence = userRepository.findById(user.getId())
				.orElseThrow( () -> {
					return new IllegalArgumentException("회원 찾기 실패");
				});
		System.out.println("서비스로 옴 user값은? "+user );
		System.out.println("persistence 객체는? :" +persistence);
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		
		persistence.setPassword(encPassword);
		persistence.setEmail(user.getEmail());
		System.out.println("수정된 persistence는? :"+persistence);
		
		// 회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료
		// = commit 이 자동으로 된다. => 영속화 된 persistence 객체의 변화가 감지되면 
		// 더티체킹이 되어 update문을 날려준다.
		//수정이 성공적으로 되어서 DB에 데이터가 변경 되었다.
		//하지만 화면에 회원정보를 눌러보면 값이 바뀌지 않았다. 로그아웃하고 다시 로그인해야
		//회원정보에 값이 변경된것을 확인할 수 있다.
		//이때 세션을 강제로 변경? ?? 시켜야 한다.
	}

}
