package com.example.bookHub.book.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;


/**
 * @Entity : 스프링부트에서 해당 클래스를 엔티티 클래스로 관리. 
 * - javax.persistence.Entity 네임스페이스에 속한다
 * @Data : 롬복 어노테이션
 * - 자동으로 자바 빈즈의 getter, setter, toString, equals, hashCode 를 만들어준다
 * @Builder : 롬복 어노테이션
 * - 객체 생성 시, 빌더 패턴으로 생성할 수 있게 도와준다(서비스 클래스 참조)
 */
@Entity
@Data
@Builder
public class Book {
	
	/* 유일 식별자 bookId 선언
	 * @Id : 데이터베이스 행 유일 식별자(PK)를 나타냄
	 * - javax.persistence.Id 네임스페이스에 속한다
	 * @GeneratedValue : 자동생성되는 값 의미
	 * - GenerationType.IDENTITY : 데이터베이스에 키 생성을 위임한다.(PK 자동증가)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;
	
	/* @Column : 열 특성 나타냄
	 * - length : 문자열 길이 제한
	 * - name : 컬럼명
	 * - nullable : null 여부 판단
	 */
	@Column(length = 200)
	private String title;
	private Integer price;
	
	/* @CreationTimestamp : 입력시 자동으로 시간 설정
	 * - LocalDateTime : 자바8에서 도입된 타입. 
	 * 
	 * +) 갱신시에도 값 수정을 원하는 경우 @UpdateTimestamp
	 */
	@CreationTimestamp
	private LocalDateTime insertDateTime;
	
}
