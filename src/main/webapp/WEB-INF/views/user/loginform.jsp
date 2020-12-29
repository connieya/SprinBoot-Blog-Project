<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<form action="/auth/loginProc" method="post" >
  <div class="form-group">
    <label for="username">아이디:</label>
    <input type="text" name="username" class="form-control" placeholder="아이디를 입력하세요" id="username">
  </div>
  
  <div class="form-group">
    <label for="password">비밀번호:</label>
    <input type="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요" id="password">
  </div>
 
  <button id="btn-login" class="btn btn-primary">로그인</button>
  <a href="https://kauth.kakao.com/oauth/authorize?client_id=9b8be57efcd2106ec313b76604b5e674&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code">
  <img height="39px;" src="/image/kakao_login.png" /></a>
</form>

<!--  시큐리티로 커스터 마이징 하여 로그인을 할 것이기 때문에
 button을 form 태그 안에 넣어서 script에 있는 user.js를 사용 안하기로함 -->

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>

