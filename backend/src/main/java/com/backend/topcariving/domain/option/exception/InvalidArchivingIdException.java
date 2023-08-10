package com.backend.topcariving.domain.option.exception;

public class InvalidArchivingIdException extends RuntimeException {
	public static final String INVALID_ARCHIVING_ID = "차량 아카이빙 아이디가 유효하지 않습니다.";

	public InvalidArchivingIdException() {
		super(INVALID_ARCHIVING_ID);
	}
}
