package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(ExceptionStatus.USER_NOT_FOUND.getMessage());
	}
}
