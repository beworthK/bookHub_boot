package com.example.bookHub.book.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bookHub.book.dto.BookCreateDTO;
import com.example.bookHub.book.dto.BookEditDTO;
import com.example.bookHub.book.dto.BookEditResponseDTO;
import com.example.bookHub.book.dto.BookReadResponseDTO;
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
	
	/**
	 * 책 정보 읽기
	 * 
	 * {bookId} - HTTP 주소를 통해 입력받는다
	 * @GetMapping - 경로 매개변수 정의
	 * @param bookId
	 * @return
	 */
	@GetMapping("/book/read/{bookId}")
	public ModelAndView read(@PathVariable Integer bookId) {
		
		// 스프링에서 데이터(Model)와 화면(View)을 함께 담을 수 있는 객체
		ModelAndView mav = new ModelAndView();
		
		// try - catch @ControllerAdvice를 통해 공통 영역으로 분리하는 것이 일반적
		try {
			
			BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
			
			// addObject( 뷰에서 사용할 이름, 뷰에서 사용할 값)
			mav.addObject("bookReadResponseDTO", bookReadResponseDTO);
			// setViewName - 뷰 경로 지정
			mav.setViewName("book/read");
			
		} catch (NoSuchElementException ex) {
			// 책 정보가 없다면(=예외발생) 실행되는 코드
			
			// UNPROCESSABLE_ENTITY = 422 오류 (서버가 요청을 이해하고 요청문법도 올바르지만, 요청된 지시를 따를 수 없음)
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY); // 보통 400오류지만 여기선 명확한 메세지송출을 위해 422 오류로 지정 
			mav.addObject("message", "책 정보가 없습니다.");
			mav.addObject("location", "/book");
			mav.setViewName("common/error/422"); // 오류 발생 시 오류 경로의 뷰를 보여주기
			
		}
		
		return mav;
	}

	/**
	 * 책 정보 수정페이지 이동 메소드
	 * @param bookId
	 * @return
	 * @throws NoSuchElementException 
	 * - try-catch 로 잡지 않고, @ExceptionHandler 를 컨트롤러에 추가함으로써 가독성을 높인다
	 * - 컨트롤러 메소드에 반드시 throws 를 지정할 필요는 없지만, 
	 *   만약 throws 가 없다면 오류가 난ㅆ을대 어떤 식으로 처리되는지 일일이 메소드 정의를 확인해야 하므로
	 *   코드의 명시성을 위해 일부러 throws NoSuchElementException 코드를 추가한다.
	 */
	@GetMapping("/book/edit/{bookId}")
	public ModelAndView edit(@PathVariable Integer bookId) throws NoSuchElementException {
		ModelAndView mav = new ModelAndView();
		
		BookEditResponseDTO bookEditResponseDTO = this.bookService.edit(bookId);
		mav.addObject("bookEditResponseDTO", bookEditResponseDTO);
		mav.setViewName("book/edit");
		
		return mav;
	}
	
	/**
	 * 책 정보 수정 메소드
	 * @param bookEditDTO
	 * @param errors
	 * @return
	 */
	@PostMapping("/book/edit/{bookId}")
	public ModelAndView update(@Validated BookEditDTO bookEditDTO,	Errors errors) {
		
		// errors.hasErrors() : 오류 여부 확인
		if (errors.hasErrors()) {
			
			// getFieldErrors() - 오류가 나 항목의 목록 가져오기
			// stream() - 스트림으로 바꾼다 (자바8부터)
			// map(x -> x.getField() + " : " + x.getDefaultMessage()) - "필드명 : 오류메세지" 형태로 적용, -> 는 람다식(개별항목 -> 적용할 함수) 
			// collect(Collectors.joining("\n")) - 줄바꿈 문자를 구분자로 하여 문자열을 합친다.
			String errorMessage = errors.getFieldErrors()
										.stream()
										.map(x -> x.getField() + " : " + x.getDefaultMessage())
										.collect(Collectors.joining("\n"));
			
			/* 위 스트림 코드의 기존 코드스타일 방식
			 List<String> errorMessages = new LinkedList<>();
			 for(FieldError fieldError : errors.getFieldErrors()) {
				String lineErrorMessage = fieldError.getField() + " : " + fieldError.getDefaultMessage();
				errorMessages.add(lineErrorMessage);
			 }
			 String errorMessage = String.join("\n", errorMessages);
			 */
			
			// 유효성 검사가 실패한다면 422 오류 페이지를 보여준다
			return this.error422(errorMessage, String.format("/book/edit/%s", bookEditDTO.getBookId()));
		}
		
		// 책 정보 수정
		this.bookService.update(bookEditDTO);
		
		// 읽기 페이지로 이동
		ModelAndView mav = new ModelAndView();
		mav.setViewName(String.format("redirect:/book/read/%s", bookEditDTO.getBookId()));
		
		return mav;
	}
	
	/**
	 * 책 정보 삭제 메소드
	 * @param bookId // 매개변수가 하나 뿐(bookId) 이므로 dto 대시 바로 파라미터를 입력받는다
	 * @return
	 * @throws NoSuchElementException
	 */
	@PostMapping("/book/delete")
	public String delete(Integer bookId) throws NoSuchElementException{
		this.bookService.delete(bookId);
		return "redirect:/book/list"; // 삭제 후 목록으로 이동
	}
	
	
	/**
	 * noSuchElementExceptionHandler
	 * 
	 * NoSuchElementException 를 매번 try-catch 로 잡지 않고, 
	 * 자동으로 처리해주는 메소드
	 * @ExceptionHandler
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
		
		/*
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message", "책 정보가 없습니다.");
		mav.addObject("location", "book/list");
		mav.setViewName("common/error/422");
		
		return mav;
		*/
		
		// 상위 코드를 error422 메소드로 빼고, 예외처리 메소드는 하기와 같이 간략화 한다.
		return this.error422("책 정보가 없습니다.", "/book/list");
	}
	
	/**
	 * 422 오류 처리 메소드
	 * - 책 정보가 없을 때, 유효성 검사가 실패할 경우에도 난다고 가정.
	 * 
	 * @param message
	 * @param location
	 * @return
	 */
	private ModelAndView error422(String message, String location) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message", message);
		mav.addObject("location", location);
		mav.setViewName("common/error/422");
		return mav;
	}
	
}
