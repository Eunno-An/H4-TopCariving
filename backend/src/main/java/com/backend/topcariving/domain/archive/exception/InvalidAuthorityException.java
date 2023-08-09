package com.backend.topcariving.domain.archive.exception;

// todo: User로 이동
public class InvalidAuthorityException extends RuntimeException {
	public static final String INVALID_AUTHORITY = "이 사용자는 권한이 없습니다.";

	public InvalidAuthorityException() {
		super(INVALID_AUTHORITY);
	}
}
