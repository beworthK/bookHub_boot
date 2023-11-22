package com.example.bookHub.book.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository
 *  - JPA 에서는 DB와 연동하는 방법 중 하나로 리포지터리 인터페이스를 사용한다
 *  - 리포지터리 인터페이스는 주로 이름 뒤에 Repository 를 붙여서 명명한다.
 *  - <T, ID> : JpaRepository 는 Generic Type 2개를 필요로 한다
 *              T 는 엔티티 타입, ID 는 엔티티의 PK 타입
 *              
 *  +) repository는 entity 객체와 함께 DB에 접근하는 객체이므로 entity 패키지 안에 넣었다.
 *     repository 패키지를 따로 만드는 경우도 있다.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

	/* 목록 메소드
	 * - JPA 에서 [검색] 을 위해서는 리포지터리 인터페이스를 
	 *   findBy{멤버변수명}(멤버변수타입, 매개변수) 형식으로 사용한다.
	 * - Pageable : 페이징 정렬 정보를 담고 있는 인터페이스
	 * - List<Book> : JpaRepository 에서 반복가능한 Iterable 인터페이스를 구현한 타입은 자동으로 목록으로 반환한다.   
	 * - 인터페이스 이므로 구현체는 없다.
	 */
	// like '%title%' as contain
	public List<Book> findByTitleContains(String title, Pageable pageable);
	
	
	// like 'title%' as startswith
 	// public List<Book> findByTitleStartsWith(String title, Pageable pageable);
 	
 	// like '%title' as endwith
  	// public List<Book> findByTitleEndsWith(String title, Pageable pageable);
  	
  	// = 'title'
  	// public List<Book> findByTitle(String title, Pageable pageable);
	
}
