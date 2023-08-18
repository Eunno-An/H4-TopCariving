package com.backend.topcariving.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.exception.InvalidMyCarIdException;
import com.backend.topcariving.domain.option.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.exception.InvalidCategoryException;
import com.backend.topcariving.domain.user.exception.UserException;

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

	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> invalidUserNotFoundHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidOauthException.class)
	public ResponseEntity<String> invalidOauthHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidArchivingIdException.class)
	public ResponseEntity<String> invalidArchivingIdHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCategoryException.class)
	public ResponseEntity<String> invalidCategory(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidMyCarIdException.class)
	public ResponseEntity<String> invalidMyCarIdHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
