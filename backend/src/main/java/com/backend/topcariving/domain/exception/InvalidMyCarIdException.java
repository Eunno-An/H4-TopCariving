package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidMyCarIdException extends RuntimeException {

	public InvalidMyCarIdException() {
		super(ExceptionStatus.INVALID_MY_CAR_ID.getMessage());
	}
}
