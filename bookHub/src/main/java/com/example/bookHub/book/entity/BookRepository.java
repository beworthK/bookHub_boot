package com.example.bookHub.book.entity;

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

}
