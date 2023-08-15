package com.backend.topcariving.domain.archive.exception;

public class InvalidMyCarIdException extends RuntimeException {

	public static final String INVALID_MY_CAR_ID = "내 차 아이디가 유효하지 않습니다.";

	public InvalidMyCarIdException() {
		super(INVALID_MY_CAR_ID);
	}
}
