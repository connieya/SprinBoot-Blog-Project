package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아니라 데이터를 리턴해준다.
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e ) {
			
			return "삭제에 실패했습니다. 해당 id는 없습니다";
		}
		
		
		return "삭제 완료";
		
	}
	
	//save함수는 id를 전달하지 않으면 insert를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	// email , password , // Json 데이터로 받으려면 @RequestBody가 필요하다.
	
	 // 함수 종료시에 자동 commit이 된다. commit이 되는데 save 함수랑 무슨 상관?
	// 영속성 컨텍스트로 인해서!!  update문을 수행하는 이유 영속화 된 데이터의 변경을 감지했기 때문에
	// 이렇게 수정해주는 것이 더티체킹이다.!!  더티체킹을 이용한 update
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email :" + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
			}
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		// 더티 체킹
		return user;
	}
	
	@GetMapping("/dummy/user")
	public List<User> list(){
		
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user/page")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable ){
		
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		//이거 사용해도 됨
		if(pagingUser.isLast()) {
			
		}
		List<User> users = pagingUser.getContent();
				
		return pagingUser;
		// return 값으로 paginUser , users로 받는 것은 차이가 있음.
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
