package com.example.bookHub.book.dto;

import java.time.LocalDateTime;

import com.example.bookHub.book.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 책 정보를 읽어서 응답으로 내보내기 위한 DTO 객체
 * 
 * 실제 HTTP 응답이 테이블 하나만 대상으로 하는 경우는 거의 없기 때문에
 * 응답별로 엔티티 집합을 묶어주는 응답 DTO 를 따로 정의한다.
 * 
 * @NoArgsConstructor
 *  매개변수가 없는 생성자를 자동으로 생성해준다.
 * @Getter
 *  BookReadResponseDTO 객체를 타임리프에서 사용할 때, 자바빈즈의 getter 규칙에 따라 테이블을 가져온다.
 */

@NoArgsConstructor  
@Getter
public class BookReadResponseDTO {
	
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	/**
	 * fromBook
	 * - Book 엔티티를 매개변수로 받아서 내부의 값을 채우는 메소드
	 * 
	 * 서비스 레이어에서 값을 채워넣을 수도 있고, 엔티티에서 응답객체로 바꿀 수도 있지만
	 * 이렇게 정보가 모이는 곳에 데이터를 설정하는 로직을 모아두면 
	 * 나중에 데이터 설정방법을 찾아볼 때 편합니다.
	 * @param book
	 * @return
	 */
	public BookReadResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		
		return this;
	}

	/**
	 * BookFactory
	 * - fromBook 메소드를 감싸는 정적(static) 메소드
	 * - 객체를 만들고, 값을 설정하기 위해 fromBook 메소드를 호출하는 일을 줄여준다.
	 * - 정적 메소드로 객체를 생성하는 방법을 [팩토리 패턴] 이라고 부른다
	 * 
	 * @param book
	 * @return
	 */
	public static BookReadResponseDTO BookFactory(Book book) {
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}
	
	
	/**
	 *  Book 정보 이외에 Review 정보도 받아야 하는 경우 예시
	 */
	/*  
	 * -----------------------------------------------------------------------------
	 public BookReadResponseDTO fromReview(Review review) {
		return this;
	 }
	 
	 // BookFactory 오버로딩 
	 public static BookReadResponseDTO BookFactory(Review review) {
	 	BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
	 	
	 	bookReadResponseDTO.fromReview(review); // Review 정보 받는 경우
	 	
	 	return bookReadResponseDTO;
	 }
	 
	
	 public static BookReadResponseDTO BookFactory(Book book, Review review) {
	 	BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
	 	
	 	// 책과 리뷰 정보 동시에 설정하는 경우
	 	bookReadResponseDTO.fromBook(book).fromReview(review);
	 	
	 	return bookReadResponseDTO;
	 }
	 -----------------------------------------------------------------------------
	 */
	
	
}