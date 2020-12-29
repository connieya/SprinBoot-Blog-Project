package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board ,@AuthenticationPrincipal PrincipalDetail principal) {
		
		System.out.println("BoardApiController 호출 post => service ㄱ");
		System.out.println("board 값:  "+board);
		System.out.println("user값인 principal : "+principal+"ㄴㄴ"+principal.getUsername());
		
		boardService.글쓰기(board, principal.getUser());
	
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}

	@PutMapping("api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		
		boardService.글수정하기(id,board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
		
	}
	
	@PostMapping("api/board/{boardId}")
	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){
		
		System.out.println("댓글 버튼 누름-> 게시글 번호 : "+boardId );
		System.out.println("reply 객체 :" +reply );
		System.out.println("user 객체인 principal : "+ principal);
		
		boardService.댓글쓰기(principal.getUser(),boardId,reply);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
}
