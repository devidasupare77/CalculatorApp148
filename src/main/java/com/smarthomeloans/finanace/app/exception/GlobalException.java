package com.smarthomeloans.finanace.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<String> recordNotFoundExceptionHandler(RecordNotFoundException e){
		
		
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		
	}

}
