package com.example.bookHub.book.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.example.bookHub.book.entity.Book;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *  BookEditDTO
 *  - 클라이언트에서 수정할 책 정보를 보내줬을 때 담는 객체
 */
@Getter
@Setter
public class BookEditDTO {

	@NonNull
	@Positive // 값을 양수로 제한한다
	private Integer bookId;
	
	@NonNull
	@NotBlank // != null && .equals("") == false (Null 이거나 문자열이 비어있으면 유효성검사 실패)
	private String title;
	
	@NonNull
	@Min(1000) // 최소값 보다 작으면 유효성 검사 실패
	private Integer price;

	/**
	 * 클라이언트가 요청한 값으로 책 엔티티를 채우는 메소드
	 * - 빌더 패턴을 사용하지 않았다. JPA 에 의해 이미 값이 채워진 엔티티를 다루는 '수정' 기능이므로
	 * = insert_Date_time 은 지정 안해도 알아서 갱신된다. JPA 가 동작할때 모든 열에 대해 데이터를 갱신하므로 쿼리에 포함됨
	 * @param book
	 * @return
	 */
	public Book fill(Book book) {
		book.setTitle(this.title);
		book.setPrice(this.price);
		return book;
	}
}
