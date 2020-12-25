package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리 할수 있게 하는것

@Configuration // 빈등록(IoC 관리)                          (시큐리티 필터가 등록이 된다.)
@EnableWebSecurity // 시큐리티 필터 추가 => 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.!!
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// /auth ~ 로 들어오는 것은 누구나 들어올수 있다.
		// 특별한  인증이 필요없다. ex) 회원가입 /auth/joinform , 로그인/auth/loginform
		http
			.authorizeRequests()
			.antMatchers("/auth/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/auth/loginform");
	
		
	//anyRequest~~~ = 그 외에 다른 요청은 인증을 해야 되!!!!
	// and()~ loginPage() 로그인 페이지는 해당 url에 요청한 페이지로 하겠다.!!
	}

}
