package com.backend.topcariving.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.topcariving.domain.exception.AlreadyExistUserException;
import com.backend.topcariving.domain.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.exception.InvalidMyCarIdException;
import com.backend.topcariving.domain.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.exception.InvalidCategoryException;
import com.backend.topcariving.domain.exception.UserNotFoundException;
import com.backend.topcariving.global.auth.exception.InvalidOauthException;
import com.backend.topcariving.global.dto.FailureResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidCarOptionIdException.class)
	public ResponseEntity<FailureResponse> invalidCarOptionIdHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_CAR_OPTION_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(InvalidAuthorityException.class)
	public ResponseEntity<FailureResponse> invalidAuthorityHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_AUTHORITY);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(AlreadyExistUserException.class)
	public ResponseEntity<FailureResponse> invalidAlreadyExistUserHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.ALREADY_EXIST_USER);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<FailureResponse> userNotFoundHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.USER_NOT_FOUND);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(InvalidOauthException.class)
	public ResponseEntity<FailureResponse> invalidOauthHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_OAUTH);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(InvalidArchivingIdException.class)
	public ResponseEntity<FailureResponse> invalidArchivingIdHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_ARCHIVING_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(InvalidCategoryException.class)
	public ResponseEntity<FailureResponse> invalidCategory(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_CATEGORY);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(InvalidMyCarIdException.class)
	public ResponseEntity<FailureResponse> invalidMyCarIdHandler(Exception e) {
		FailureResponse response = new FailureResponse(ExceptionStatus.INVALID_MY_CAR_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
