package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	@Transactional
	public void 글쓰기(Board board, User user) { //title, content
		
		board.setCount(0);
		board.setUser(user);
		System.out.println("bordService의 board값 : "+board);
		boardRepository.save(board);
		
	}
	
	@Transactional
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	public Board 글상세보기(int id) {
		
		// 해당 id값을 통해 board안에 있는 데이터를 불러온다.
		// 이때 DB에는 없지만 reply 객체 데이터는 Eager 전략으로 fetch로 불러올 수 있다.
		return boardRepository.findById(id)
				.orElseThrow( () -> {
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을수없습니다");
				});
	}
	
	@Transactional()
	public void 글삭제하기(int id) {
		

		 boardRepository.deleteById(id);
			
	}
	
	@Transactional // 더티체킹 flush를 위해 선언해야하는 어노테이션
	public void 글수정하기(int id, Board requestBoard) {
		
		//수정하려면 영속화를 먼저 시켜야한다. 영속화 ㄱㄱ
		Board board = boardRepository.findById(id)
				.orElseThrow( () -> {
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을수없습니다");
				});
		// 영속성 컨텍스트에 board가  들어왔다.
		//영속화 완료
		
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	
		//해당 함수 종료시 =(Service가 종료 될때) 트랜잭션이 종료된다.
		//이때 더티체킹이 일어난다. -> 자동 업데이트 flush
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replyDto) {
		
		User user = userRepository.findById(replyDto.getUserId())
				.orElseThrow( () -> {
					return new IllegalArgumentException("댓글 쓰기 실패: 유저 id를 찾을수없습니다");
				}); // 영속화 완료
		
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
		
	}
	
//	@Transactional
//	public void 댓글쓰기(User user, int boardId ,Reply replyRequest ) {
//		
//	Board board = boardRepository.findById(boardId)
//			.orElseThrow( () -> {
//				return new IllegalArgumentException("게시글 찾기 실패: 글을 찾을수없습니다");
//			});
//	
//	System.out.println("서비스로 넘어옴->");
//	System.out.println("user 객체 : "+user);
//	System.out.println("replyRequest:" +replyRequest);
//	System.out.println("boardId: "+boardId);
//	System.out.println("board 객체 :" + board);
//		
//		replyRequest.setUser(user); 
//		replyRequest.setBoard(board);
//		
//		System.out.println("댓글 객체에 값 담아서 DB ㄱ: "+replyRequest);
//		
//		replyRepository.save(replyRequest);
//		
//	}
	
}
