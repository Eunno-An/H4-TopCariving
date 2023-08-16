package com.backend.topcariving.global.exception;

public class InvalidTokenException extends RuntimeException {
	private static final String INVALID_TOKEN = "토큰이 유효하지 않습니다.";

	public InvalidTokenException() {
		super(INVALID_TOKEN);
	}
}
