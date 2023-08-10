package com.backend.topcariving.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.option.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidCarOptionIdException.class)
	public ResponseEntity<String> invalidCarOptionIdHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidAuthorityException.class)
	public ResponseEntity<String> invalidAuthorityHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidArchivingIdException.class)
	public ResponseEntity<String> invalidArchivingIdHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
