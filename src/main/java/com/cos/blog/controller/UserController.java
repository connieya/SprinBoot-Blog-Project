package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody Object kakaoCallback(String code) { // data를 리턴해주는 컨트롤러 함수
		
		
		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate
		
		//HttpHeader 오브젝트 생성
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// body 데이터가 key - value 이다
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "9b8be57efcd2106ec313b76604b5e674");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code",code );
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post 방식으로 ~ 그리고 response 변수의 응답 받음
		ResponseEntity response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				
				);
		// Gson , Json , Simple , ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		
		//OAuthToken oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		
		return response.getBody();
		
	}
	
}
