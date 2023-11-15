package com.example.bookHub.book.dto;

import java.time.LocalDateTime;

import com.example.bookHub.book.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BookEditResponseDTO : 책 수정화면 응답 DTO
 * 
 * '수정' 에 관련된 클래스가 '생성'에 관련되 클래스에 의존하는 경우
 * 자신과 관련없는 변경으로 인해서 오류가 날 경우를 방지하기 위해서
 * BookReadResponseDTO 와 동일한 형태지만 따로 클래스를 두었다.
 */

@NoArgsConstructor  
@Getter
public class BookEditResponseDTO {
	
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	public BookEditResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		
		return this;
	}

	public static BookEditResponseDTO BookFactory(Book book) {
		BookEditResponseDTO bookEditResponseDTO = new BookEditResponseDTO();
		bookEditResponseDTO.fromBook(book);
		
		return bookEditResponseDTO;
	}
	
	
	
}