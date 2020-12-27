package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리 할수 있게 하는것

@Configuration // 빈등록(IoC 관리)                          (시큐리티 필터가 등록이 된다.)
@EnableWebSecurity // 시큐리티 필터 추가 => 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.!!
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	
	
	@Bean //이제 어디서든 쓸 수 있다.
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean // loC가 된다.
	public BCryptPasswordEncoder encodePWD() {
		
		// 잘모르겠으면 test.EncTest.java 참고
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// /auth ~ 로 들어오는 것은 누구나 들어올수 있다.
		// 특별한  인증이 필요없다. ex) 회원가입 /auth/joinform , 로그인/auth/loginform
		http	
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
			.antMatchers("/", "/auth/**" ,"/js/**","/css/**" ,"/image/**","/dummy/**")
			.permitAll()                                                   //test하기 위해 dummy에도 인증 권한 품
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/auth/loginform")
			.loginProcessingUrl("/auth/loginProc")
			.defaultSuccessUrl("/"); 
		
 // 스프링 시큐리티가 해당 주소로 요청 오는 로그인을 가로챈다.
	
		
	//anyRequest~~~ = 그 외에 다른 요청은 인증을 해야 되!!!!
	// and()~ loginPage() 로그인 페이지는 해당 url에 요청한 페이지로 하겠다.!!
	}

}
