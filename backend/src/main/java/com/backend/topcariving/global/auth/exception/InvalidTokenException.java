package com.backend.topcariving.global.auth.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException() {
		super(ExceptionStatus.INVALID_TOKEN.getMessage());
	}
}
