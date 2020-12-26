package com.cos.blog.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
//자동으로 bean 등록이 된다.
//@repository 생략 가능함
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//select * from user where username= ?;
	Optional<User> findByUsername(String username);
	
	
	
	// 시큐리티로 로그인을 할 것이기 때문에 밑에 naming은 사용 안함
	//JPA naming 전략
	//Select * from user where username =? and password =?;
	//User findByUsernameAndPassword(String username, String password);
	
//	@Query(value="Select * from user where username =? and password =?", nativeQuery =true )
//	User login(String username, String password);
}
