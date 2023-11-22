package com.example.bookHub.book.dto;

import lombok.Getter;

/**
 * 책 목록을 클라이언트에 응답하기 위한 DTO 객체를 만듭니다.ㄴ
 */
@Getter
public class BookListResponseDTO {
	
	private Integer bookId;
	private String title;

	/* 생성자를 이용해서 객체를 생성한다. 
	    변하지 않는 객체를 생성할 때 사용하는 패턴.
	    멤버 변수는 private 로 선언되어 있고, 
	    setter 도 없기 때문에 객체를 생성할 때 외에는 값을 바꿀 수 없다(=immutable)
	   @AllArgsConstructor 를 쓰지 않는이유
	    추후 응답객체가 바뀔 때 [필수가 아닌] 항목들이 추가될 수 있기 때문
	 */
	public BookListResponseDTO(Integer bookId, String title) {
		this.bookId = bookId;
		this.title = title;
	}
	
	/* 공동저자(필수가 아닌 항목) 이 추가되는 생성자
	 * - @AllArgsConstructor를 쓰지 않고 생성자를 따로 만들어서 코드가 의도를 나타내게 한다. 
	 */
	//public BookListResponseDTO(Integer bookId, String title, Optional<String> commonAuthor) {
	//	this.bookId = bookId;
	//	this.title = title;
	//	this.commonAuthor = commonAuthor;
	//}
	
}
