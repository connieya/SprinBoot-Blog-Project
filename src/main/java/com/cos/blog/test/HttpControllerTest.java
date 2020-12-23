package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller 사용자가 요청 -> 응답(HTML 파일)

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	
	private static final String TAG = "HttpControllerTest: " ;
	
	//yml 설정 후 localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m =   new Member(1,"gg","1234","222@s");
		
		System.out.println(TAG+"getter:" +m.getId());
		m.setId(5000);
		System.out.println(TAG+"getter:" +m.getId());
		System.out.println(TAG+m.getUsername());
		System.out.println(m.getPassword());
		
		return "lombok test 완료: "+m.getUsername();
	}
	
	//인터넷 브라우저 요청은 무조건 get요청 밖에 할 수 없다.
	//http://localhost:8080/http/get(select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		System.out.println(m.getId());
		
		return "get 요청:"+ m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	

}
