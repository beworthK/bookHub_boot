package com.example.bookHub.book.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *  책 입력 DTO
 * 
 *  DTO(Data Transfer Object)는 값을 담는 컨테이너 객체
 *  클라이언트가 서버로 전달한 값들을 담는 역할
 *  해당 프로젝트에서너는 클라이언트의 http 파라미터를 담는 컨테이너 객체로 사용된다
 *  DTO 객체를 따로 만들어두면, 나중에 DB는 그대로이지만 화면 UI가 변경되는 경우 대처가 쉬워진다.
 *  그러므로 createDTO 와 editDTO 를 분리하였다.
 *  
 *  @Getter/@Setter : @Data 어노테이션의 하위집합. 자바빈즈의 getter,setter를 자동으로 만들어준다
 */
@Getter
@Setter
public class BookCreateDTO {

	/* @NonNull
	 * 	- 값이 null 인 경우 NullPointerException을 발생시켜준다.
	 * 	- 반드시 값이 있다는걸 보장하기 위해 @NonNull 어노테이션 사용
	 */
	@NonNull
	private String title;
	
	@NonNull
	private Integer price;
	
}
