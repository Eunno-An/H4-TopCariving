package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidCarOptionIdException extends RuntimeException {

	public InvalidCarOptionIdException() {
		super(ExceptionStatus.INVALID_CAR_OPTION_ID.getMessage());
	}
}
