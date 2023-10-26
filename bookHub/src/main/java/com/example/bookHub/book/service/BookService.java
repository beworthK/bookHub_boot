package com.example.bookHub.book.service;

import org.springframework.stereotype.Service;

import com.example.bookHub.book.dto.BookCreateDTO;
import com.example.bookHub.book.entity.Book;
import com.example.bookHub.book.entity.BookRepository;

/**
 * @Service
 * 	- 서비스 클래스는 실제 비즈니스 로직 흐름이 실행되는 곳.
 *  - HTTP 계층과 무관하게 여러 엔티티 혹은 다른 서비스 레이어를 이용해서 원하는 결과를 얻어내는 레이어이다.
 */
@Service
public class BookService {

	/* BookRepository
	 * 	- DB 와 통신하기 위해 repository 선언
	 * 	- 합성(Composition) : 클래스 안에서 다른 클래스의 인스턴스를 가지고 있는 것
	 *  - 해당 객체(BookRepository)를 인스턴스화 하지 않아도 빈으로 등록된 객체는 스프링 프레임워크가 직접 인스턴스화 한다.
	 */
	private BookRepository bookRepository;

	/* 
	 * BookService '생성자'
	 * 	- 생성자 의존성 주입 : 생성자를 통해서 외부 자원을 주입하는 방식
	 */
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	/*
	 * 책 생성을 위한 객체 BookCreateDTO 를 매개변수로 받는 메소드
	 */
	public Integer insert(BookCreateDTO bookCreateDTO) {
		
		// 기존에 사용하던 보일러플레이트 코드
		//Book book = new Book();
		//book.setTitle(bookCreateDTO.getTitle());
		//book.setPrice(bookCreateDTO.getPrice());
		
		// 위 보일러플레이트 코드를 없애기 위해 하위와 같이 빌더패턴을 사용	 
		Book book = Book.builder()                   // 1. 객체를 생성할 빌더 객체(BookBuilder 객체)를 만들고
					.title(bookCreateDTO.getTitle()) // 2. 멤버변수(값) 형식으로 데이터를 셋팅한 후
					.price(bookCreateDTO.getPrice())
					.build();                        // 3. 빌더 객체에서 실제 객체(Book 객체)를 만든다		

		this.bookRepository.save(book); // DB에 저장
		
		return book.getBookId(); // PK 값을 반환하여 바로 보기 화면으로 이동한다
	}
}

