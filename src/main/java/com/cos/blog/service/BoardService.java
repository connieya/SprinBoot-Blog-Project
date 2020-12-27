package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	
	@Autowired
	private BoardRepository boardRepository;
	
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
	
}
