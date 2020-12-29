package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
	private int id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <Html>태그가 섞임

	//@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Board = Many, User = One
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트를 저장할 수없다. FK,자바는 오브젝트를 저장할 수 있다
	
	// mapperdBy 뒤에 board는 Reply.java에 있는 board이다.
	@OneToMany(mappedBy = "board", fetch=FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다.(난 fk가 아니에요)
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> reply;
	// DB에는 reply 칼럼이 없다.
		// select 를 할때 DB에는 없지만 ,Eager 전략이기 때문에 fetch해서 바로 들고 올거다!! 
	
	
	
	@CreationTimestamp
	private Timestamp createDate;
}
