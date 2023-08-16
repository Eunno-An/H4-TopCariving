package com.backend.topcariving.domain.user.exception;

public class UserNotFoundException extends RuntimeException{

	private static final String USER_NOT_FOUND = "유저 정보가 없습니다.";

	public UserNotFoundException() {
		super(USER_NOT_FOUND);
	}
}
