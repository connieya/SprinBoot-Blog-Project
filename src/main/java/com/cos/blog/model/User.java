package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른 언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySql에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
//@DynamicInsert //insert시에 null인 값을 제외시켜줌
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에 연결된 DB의 전략을 따라감
	private int id; // 시퀀스, auto-increment
	
	@Column(nullable = false , length=30 , unique = true)
	private String username; //아이디
	
	@Column(nullable = false , length=100)  // 비밀번호 -> 해쉬값으로 할거라서 넉넉히
	private String password;
	
	@Column(nullable = false , length=50)
	private String email;
	
	//@ColumnDefault("'user'") //String -> RoleType으로 변경
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. , role을 부여(admin,user,manager)
	// String -> Enum (domain이 있다 ex) 성별은 남, 여 2개 초딩은 1~6
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
}
