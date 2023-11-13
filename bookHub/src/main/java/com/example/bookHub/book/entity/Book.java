package com.example.bookHub.book.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Entity : 스프링부트에서 해당 클래스를 엔티티 클래스로 관리. 
 * - javax.persistence.Entity 네임스페이스에 속한다
 * @Data : 롬복 어노테이션
 * - 자동으로 자바 빈즈의 getter, setter, toString, equals, hashCode 를 만들어준다
 * @Builder : 롬복 어노테이션
 * - 객체 생성 시, 빌더 패턴으로 생성할 수 있게 도와준다(서비스 클래스 참조)
 * @NoArgsConstructor: 
 * - JPA에서 엔티티 값을 채울때 필요. 
 *   JPA는 엔티티의 인스턴스를 생성하고, 데이터베이스에 있는 값을 읽어서 setter를 통해 인스턴스의 멤버변수 값을 채워나간다.
 *   그러므로 JPA가 엔티티의 인스턴스를 생성할 때는 매개변수 없는 생성자가 있어야 함
 * @AllArgsConstructor  
 * - 모든 멤버변수를 매개변수로 설정할 수 있는 생성자를 자동으로 만들어주는 어노테이션.
 *   기존에 해당 어노테이션이 없어도 @Builder 어노테이션이 있는 클래스에 아무런 생성자가 없다면
 *   롬복이 자동으로 해당 어노테이션을 붙여주므로 오류가 생기지 않는다.
 *   +) 그러나 다른 생성자가 있다면 자동으로 붙이지 않는다!
 *      본 코드에서는 JPA를 위해 @NoArgsConstructor 어노테이션을 사용하면서 생성자가 생겼으므로
 *      추가로 해당 어노테이션을 붙여주어야 한다.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor  
@AllArgsConstructor 
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
	
	/* 
	 * @AllArgsConstructor 이노테이션을 통해 생성자를 자동으로 만들어주므로 하위처럼 명시적으로 선언할 필요 없다.
	 * 
	 * this.필드 = 매개변수
	 * - 필드와 매개변수 이름이 동일하면, 동일한 이름의 매개변수가 사용우선순위가 높아서 필드를 사용할 수 없으므로
	 *   필드 앞에 this. 를 붙인다. 
	 *   this.필드 = 객체 자신의 참조, this라는 참조 변수로 필드를 사용하는 것과 동일
	 *
	// 필드 초기화 생성자
	public Book(Integer bookId, String title, Integer price, LocalDateTime insertDateTime) {
		this.bookId = bookId;
		this.title = title;
		this.price = price;
		this.insertDateTime = insertDateTime;
	}
	*/
}
