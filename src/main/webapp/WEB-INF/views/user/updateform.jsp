<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<form >
	<input type="hidden" id="id" value="${principal.user.id}" />
	
  <div class="form-group">
    <label for="username">아이디</label>
    <input type="text" value="${principal.user.username}" class="form-control" placeholder="아이디를 입력하세요" id="username" readonly>
  </div>
  
  <div class="form-group">
    <label for="password">비밀번호</label>
    <input type="password" value="${principal.user.password}" class="form-control" placeholder="비밀번호를 입력하세요" id="password">
  </div>
  
  <div class="form-group">
    <label for="email">이메일</label>
    <input type="text" value="${principal.user.email}" class="form-control" placeholder="이메일을 입력하세요" id="email">
  </div>
  
</form>
  <button id="btn-update" class="btn btn-primary">회원수정</button>
  <button onclick="return confirm('정말로 탈퇴하시겠습니까?')" id="btn-delete" class="btn btn-danger">회원탈퇴</button>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>

