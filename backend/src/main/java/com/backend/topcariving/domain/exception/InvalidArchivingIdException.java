package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidArchivingIdException extends RuntimeException {

	public InvalidArchivingIdException() {
		super(ExceptionStatus.INVALID_ARCHIVING_ID.getMessage());
	}
}
