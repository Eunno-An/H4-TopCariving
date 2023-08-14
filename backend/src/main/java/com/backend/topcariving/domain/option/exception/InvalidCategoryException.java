package com.backend.topcariving.domain.option.exception;

public class InvalidCategoryException extends RuntimeException {
	private static final String INVALID_CATEGORY= "같은 카테고리 범주만 변경이 가능합니다";

	public InvalidCategoryException() {
		super(INVALID_CATEGORY);
	}
}
