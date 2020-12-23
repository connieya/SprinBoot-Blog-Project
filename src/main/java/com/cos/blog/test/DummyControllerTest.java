package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아니라 데이터를 리턴해준다.
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	@GetMapping("/dummy/user")
	public List<User> list(){
		
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user/page")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable ){
		
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		//이거 사용해도 됨
		if(pagingUser.isLast()) {
			
		}
		List<User> users = pagingUser.getContent();
				
		return users;
	}
	
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/4을 찾았는데 데이터베이스에 해당 데이터가 없으면 user가 null 값이 된다.
		// 그럼 return 할 때 null 이 리턴 되는데? 그럼 프로그램에 문제가 생기잖아?
		// Optional로 너의 User 객체를 감싸서 가져올게
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
			}
		});

			
		
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹 브라우저가 이해할 수 있는 데이터 -> json )
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConerter가 JackSon 라
		return user;
	}

	@PostMapping("/dummy/join")
	public String join(User user) { // key = value(약속)
		System.out.println("username :" + user.getUsername());
		System.out.println("password :" + user.getPassword());
		System.out.println("email :" + user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료";
	}
}
