package com.backend.topcariving.domain.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidCategoryException extends RuntimeException {

	public InvalidCategoryException() {
		super(ExceptionStatus.INVALID_CATEGORY.getMessage());
	}
}
