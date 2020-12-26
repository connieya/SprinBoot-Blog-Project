package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	BoardService  boardService;
	
	// 메인화면 갈때는 인증이 필요 없기 때문에 주석처리
	//@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"", "/"})
	public String index(Model model,@PageableDefault(size=3, sort="id",direction = Sort.Direction.DESC) Pageable pageable ) { // 컨트롤러에서 세션을 어떻게 찾는지?
		
		//model은 jsp로 치면 request 정보라고 보면 된다.
		model.addAttribute("boards", boardService.글목록(pageable));
		//위에 url으로 Model을 통한 데이터 boards가 넘어간다.
		//WEB-INF/views/index.jsp
	//	System.out.println("로그인 사용자 아이디 :" +principal.getUsername());
		return "index"; //@Controller로 인해 return 할 때 viewResolver가 작동!!
		//viewResolver는 application.yml에서 지정한
		//mvc:view:prefix: /WEB-INF/views/suffix: .jsp
		//의 역할을 해줌
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveform")
	public String saveForm() {
		return "board/saveform";
	}
}
