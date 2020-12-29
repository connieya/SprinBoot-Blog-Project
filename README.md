# SprinBoot-Blog-Project


## 추가 라이브러리

```
<!-- 시큐리티 태그 라이브러리 -->
		
		<dependency>
		  <groupId>org.springframework.security</groupId>
		  <artifactId>spring-security-taglibs</artifactId>
		</dependency>
		 
		<!-- JSP 템플릿 엔진 -->
		 	
		 <dependency>
		  <groupId>org.apache.tomcat.embed</groupId>
		  <artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		
		
		<!-- JSTL -->
		<dependency>
		  <groupId>javax.servlet</groupId>
		  <artifactId>jstl</artifactId>
		</dependency>

```

---------------------

## 카카오톡 로그인 OAuth 환경설정

로그인 요청 콜백 주소 : http://localhost:8000/auth/kakao/callback

User 오브젝트 : id(번호) , username , password , email
카카오로부터 받을 정보 : profile정보( 필수) , email(선택)

로그인 요청 주소 (GET)
https://kauth.kakao.com/oauth/authorize?client_id=9b8be57efcd2106ec313b76604b5e674&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code

응답 받은 코드:
http://localhost:8000/auth/kakao/callback?code=q4iI2w03I0xjr4k_SKOEIGkZF_Q8UUIcNOobgQljiR1dNT_vtT7IBXphz_Vwv4lYfFiXGQorDNQAAAF2rA5umQ

토큰 발급 요청 주소 (POST)  - http body에 데이터를 전달 (5가지 데이터를 담아라)
MIME : application/x-www-form-urlencoded;charset=utf-8 (key =value)
https://kauth.kakao.com/oauth/token 

// 토큰 뒤 주소에 전달할 데이터는 없다. 왜냐하면 post 방식이기 때문에
// 쿼리 스트링으로 전달하는게 아니라 http body에 데이터를 전달

grant_type = authorization_code
client_id = 9b8be57efcd2106ec313b76604b5e674
redirect_uri = http://localhost:8000/auth/kakao/callback
