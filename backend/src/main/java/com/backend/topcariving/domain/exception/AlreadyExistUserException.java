package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class AlreadyExistUserException extends RuntimeException {

	public AlreadyExistUserException() {
		super(ExceptionStatus.ALREADY_EXIST_USER.getMessage());
	}
}
