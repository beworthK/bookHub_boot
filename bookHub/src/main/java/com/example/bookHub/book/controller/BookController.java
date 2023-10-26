package com.example.bookHub.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bookHub.book.dto.BookCreateDTO;
import com.example.bookHub.book.service.BookService;

/**
 *  책 컨트롤러 
 *  컨트롤러 클래스는 클라이언트의 요청(request)을 받아서 내부 처리 후 응답(response)을 만들어내는 클래스
 *  = 웹브라우저(=클라이언트)에서 특정 주소를 입력했을 때 실행되는 메소드가 모여있는 클래스
 */
@Controller
public class BookController {

	/*
	 * 필드 의존성 주입 : 생성자가 아닌 private 변수를 선언하고 위에 @Autowired 어노테이션으로 의존성 주입
	 * - 생성자 DI, 필드 DI 둘 간 특별한 차이는 없지만
	 *   의존성 주입 시스템이 객체간 의존성을 판별할 때 순환참조가 일어날 가능성을 낮추고,
	 *   어노테이션 없이 더 명확하게 의존성을 주입할 수 있기 때문에
	 *   스프링 진영에서는 생성자 의존성 주입을 더 추천한다.
	 *   
	 * - 컨트롤러는 프레임워크만 호출하는 클래스 이므로 순환참조 가능성이 거의 없어서 필드 의존성 주입을 사용한다.
	 */
	@Autowired
	private BookService bookService;
	
	/**
	 * 책 생성 화면
	 * @GetMapping : http 요청 메소드 중 GET 메소드로 요청될 때만 해당 메소드 실행
	 * @return
	 */
	@GetMapping("/book/create")
	public String create() {
		return "book/create";
	}

	/**
	 * 책 입력 
	 * @param bookCreateDTO
	 * @return
	 */
	@PostMapping("/book/create")
	public String insert(BookCreateDTO bookCreateDTO) {
		// 파라미터를 입력받아서 서비스로 전달한다
		Integer bookId = this.bookService.insert(bookCreateDTO);
		
		// String.format은 문자열의 전체적인 구조를 보기 쉽게 만들고, 문자열이 아닌 객체는 자동으로 문자열로 만들어주므로 깔끔한 코드 작성시 주로 사용
		return String.format("redirect:/book/read/%s", bookId); // %s = bookId
	}

}
