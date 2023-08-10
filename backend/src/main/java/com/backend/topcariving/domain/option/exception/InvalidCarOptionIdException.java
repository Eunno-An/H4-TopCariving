package com.backend.topcariving.domain.option.exception;

public class InvalidCarOptionIdException extends RuntimeException {
	public static final String INVALID_CAR_OPTION_ID = "차량 옵션 아이디가 유효하지 않습니다.";

	public InvalidCarOptionIdException() {
		super(INVALID_CAR_OPTION_ID);
	}
}
