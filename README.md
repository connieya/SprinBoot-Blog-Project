# SprinBoot-Blog-Project
<br/>


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
----------------------------------
## 회원가입

오류 수정

**User.java**

```
@Column(nullable = false , length=30 , unique = true)
	private String username; //아이디
```
unique = true 속성으로 중복 값 X <br/>

![image](https://user-images.githubusercontent.com/66653324/103412927-d5543d00-4bba-11eb-9c96-1978c74460cd.png)

_costa라는 값이 있는 상태에서 회원가입 로직 수행_
<br/>
결과는??? <br/>
![image](https://user-images.githubusercontent.com/66653324/103412944-e7ce7680-4bba-11eb-8cc1-ca9f225933bb.png)

ajax 통신이 성공하여 회원가입 완료 문구가 나타났다. <br/>
하지만  DB에는 당연히 오류가 난다.
```
2020-12-31 22:52:35.580  WARN 8068 --- [nio-8000-exec-5] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1062, SQLState: 23000
2020-12-31 22:52:35.580 ERROR 8068 --- [nio-8000-exec-5] o.h.engine.jdbc.spi.SqlExceptionHelper   : Duplicate entry 'costa' for key 'user.UK_jreodf78a7pl5qidfh43axdfb'
2020-12-31 22:52:35.588  WARN 8068 --- [nio-8000-exec-5] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [user.UK_jreodf78a7pl5qidfh43axdfb]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute s
```
<br/>
`디버깅` 시작!
<br/>

 **GlobalExceptionHandler.java**
```
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	// IllegalArgumentException에 해당하는 에러만 받는다.
	//@ExceptionHandler(value= IllegalArgumentException.class)
	@ExceptionHandler(value= Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	
}
```
모든 Exception을 받는다.<br/>
그래서 회원가입에서 오류가 발생해도 여기서 받게 된다.<br/>
 return 값의 INTERNAL_SERVER_ERROR.value() 값은 500이다. <br/>
 
 
 따라서 ajax 통신에서 코드를 수정하였다.
 ```
 	if(resp.status == 500){
	alert("회원가입에 실패하였습니다.");
			
	}else{
	alert("회원가입이 완료되었습니다.");
	location.href="/"
	}
 
 ```
 
`디버깅 끝`
---------------------------------------------

## 회원탈퇴

*Cannot delete or update a parent row: a foreign key constraint fails (`blog`.`reply`, CONSTRAINT `FKqnspgy412rv4dfcmv69hsf4px` FOREIGN KEY (`userId`) REFERENCES `user` (`id`))*

<br/>
board 테이블과 user 테이블과 연관관계 (fk키)로 되어있어서 회원탈퇴에 실패하였다.

---------------------------------------
<br/>


## 글삭제

`디버깅` 시작 <br/>
댓글이 있을 경우 글삭제 오류 -> Board 객체와 Reply 객체는 fk키 연관관계가 있기 때문이다.

```
2021-01-01 01:56:18.339  WARN 8068 --- [nio-8000-exec-2] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1451, SQLState: 23000
2021-01-01 01:56:18.339 ERROR 8068 --- [nio-8000-exec-2] o.h.engine.jdbc.spi.SqlExceptionHelper   : Cannot delete or update a parent row: a foreign key constraint fails (`blog`.`reply`, CONSTRAINT `FKayalcledc3l0g5lt1balg0jwf` FOREIGN KEY (`boardId`) REFERENCES `board` (`id`))
2021-01-01 01:56:18.340  INFO 8068 --- [nio-8000-exec-2] o.h.e.j.b.internal.AbstractBatchImpl     : HHH000010: On release of batch it still contained JDBC statements
2021-01-01 01:56:18.346  WARN 8068 --- [nio-8000-exec-2] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement]
```

**board.java**
<br/> 
연관관계 관련 오류이기 때문에
@ManyToOne 와 같은 연관관계 설정 어노테이션에서 따로 설정해줘야함을 느꼈고,
<br/>
stackoverflow 에서 검색해보니
CascadeType.PERSIST ,CascadeType.REMOVE 이것을 입력해라고 했다.

```
@OneToMany(mappedBy = "board", fetch=FetchType.EAGER ,cascade = {CascadeType.PERSIST ,CascadeType.REMOVE})
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> reply;
```
참고 : JPA cascade 옵션

`디버깅 완료`

## 댓글쓰기

_영속화 -> 네이밍쿼리_

**BoardService.java**

영속화

```
@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replyDto) {
	
	User user = userRepository.findById(replyDto.getUserId())
				.orElseThrow( () -> {
					return new IllegalArgumentException("댓글 쓰기 실패: 유저 id를 찾을수없습니다");
				}); // 영속화 완료
		
	}
	
	Board board = boardRepository.findById(replyDto.getBoardId())
				.orElseThrow( () -> {
					return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을수없습니다");
				});
				
	Reply reply = Reply.builder()
	.user(user)
	.board(board)
	.content(replyDto.getContent())
	.build();
		
	replyRepository.save(reply);
```

user , board 객체의 id값을 받아 repository에서 데이터를 불러와 <br/>
reply 객체에 담아 로직을 수행하였다. <br/>

코드가 난잡하여 네이밍 쿼리를 사용하였다. <br/>

**ReplyRepository.java**

네이밍 쿼리 사용시 참고사항!! <br/>
- @Transactional -> @Modifying 를 사용해야함
- 리턴 값은 void, int! <br/>

```
	@Modifying
	@Query(value="insert into reply(userId, boardId, content, createDate) values(?1,?2,?3, now())",nativeQuery = true)
	void mSave(int userId, int BoardId, String content );

```


user , board 의 데이터만 받으면 바로 insert 할 수 있는 네이밍 쿼리 전략 <br/>

**네이밍 쿼리로 변경**

```
@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replyDto) {
		
		//영속화 할 필요없이 replyRepository에서 만든 naming 쿼리 사용하면 됨
		replyRepository.mSave(replyDto.getUserId(), replyDto.getBoardId(), replyDto.getContent());
		
		}

```


----------------------------------------

## 댓글삭제

댓글삭제를 수행하기 위해 댓글아이디를 파라미터로 넘겨줘야한다. <br/>
input 태그 hidden 타입을 이용하여 댓글 아이디 값을 불러왔다.
댓글 삭제도 권한이 있는 사용자만 수행 할 수 있게 <br/>
JSTL <c:choose> 를 사용하였다.<br/>
<c:when>안에 input 태그를 넣어 댓글 삭제를 성공했지만,
혹시나 해서 <c:choose>안에 넣어 보니 에러가 발생하였다. <br/>


```
org.apache.jasper.JasperException: <h3>Validation error messages from TagLibraryValidator for [c] in [/WEB-INF/views/board/detail.jsp]</h3><p>149: Illegal text inside "c:choose" tag: "<input ...".</p><p>149: Illegal text inside "c:choose" tag: "${reply...".</p><p>149: Illegal text inside "c:choose" tag: ""/>...".</p>
	at org.apache.jasper.compiler.DefaultErrorHandler.jspError(DefaultErrorHandler.java:55) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.ErrorDispatcher.dispatch(ErrorDispatcher.java:294) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.ErrorDispatcher.jspError(ErrorDispatcher.java:81) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Validator.validateXmlView(Validator.java:1915) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Validator.validateExDirectives(Validator.java:1863) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Compiler.generateJava(Compiler.java:224) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Compiler.compile(Compiler.java:386) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Compiler.compile(Compiler.java:362) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.compiler.Compiler.compile(Compiler.java:346) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.JspCompilationContext.compile(JspCompilationContext.java:605) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:400) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:385) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at org.apache.jasper.servlet.JspServlet.service(JspServlet.java:329) ~[tomcat-embed-jasper-9.0.41.jar:9.0.41]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733) ~[tomcat-embed-core-9.0.41.jar:4.0.FR]
```
JSTL 오류이다.

<c:choose> 안에 input 태그는 Illegal text로 인식한다.




