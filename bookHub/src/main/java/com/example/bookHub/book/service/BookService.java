package com.example.bookHub.book.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.bookHub.book.dto.BookCreateDTO;
import com.example.bookHub.book.dto.BookEditDTO;
import com.example.bookHub.book.dto.BookEditResponseDTO;
import com.example.bookHub.book.dto.BookReadResponseDTO;
import com.example.bookHub.book.entity.Book;
import com.example.bookHub.book.entity.BookRepository;

/**
 * @Service
 * 	- 서비스 클래스는 실제 [비즈니스 로직] 흐름이 실행되는 곳.
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
	
	/**
	 * 읽기 메소드 
	 * @param bookId // 매개변수
	 * @return
	 * @throws NoSuchElementException //orElseThrow 가 던지는 예외
	 */
	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
		
		// bookRepository - JpaRepository 인터페이스 (CRUD Repository 정의되어 있음)
		// findById 메소드는 Optional<Book> 객체를 반환.
		// Optional 객체는 값이 없으면 Optional 객체를 반환하므로 필요시에만 null체크를 할 수 있다.
		// orElseThrow - 내부값이 null 이면 예외(NoSuchElementException)를 던진다.
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		
		// 책 응답 DTO
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO; // 응답을 반환하는 BookFactory() 메소드 결과 리턴
		
	}
	
	/**
	 * 수정 메소드 - 책 수정 화면을 보여주는 기능
	 * @param bookId
	 * @return
	 * @throws NoSuchElementException
	 */
	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException {
		
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		
		/*
		BookEditResponseDTO bookEditResponseDTO = new BookEditResponseDTO();
		bookEditResponseDTO.fromBook(book);
		return BookEditResponseDTO.BookFactory(book);
		위 방식을 아래 코드 하나로 해결
		*/
		
		return BookEditResponseDTO.BookFactory(book);
	}
	
	/**
	 * 수정 기능 메소드
	 * @param bookEditDTO
	 * @throws NoSuchElementException
	 */
	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException {
		
		// 1. 데이터베이스에 저장된 책 정보 가져오기
		Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow();
		
		// 2. 입력 커맨드 객체에 필요하 필드를 추려내서 데이터베이스에 저장할 책 정보를 변경
		// cf. 어플리케이션의 책 정보만 가지고 있는 상태(데이터베이스와 연동 x)
		book = bookEditDTO.fill(book);

		// 3. 실제 데이터를 데이터베이스에 저장
		// cf. JPA 에서는 입력/수정 전부 save 메소드(pk 값 있으면 update, 없으면 insert)
		this.bookRepository.save(book);
	
	}

	/**
	 * 삭제 기능 메소드
	 * @param bookId
	 * @throws NoSuchElementException
	 */
	public void delete(Integer bookId) throws NoSuchElementException {
		
		Book book = this.bookRepository.findById(bookId).orElseThrow();
		this.bookRepository.delete(book); // JPA 에서 삭제는 delete
	
	}
	
}

