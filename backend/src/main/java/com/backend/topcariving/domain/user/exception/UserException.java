package com.backend.topcariving.domain.user.exception;

public class UserException extends RuntimeException{

	public static final String USER_NOT_FOUND = "유저 정보가 없습니다.";
	public static final String ALREADY_EXIST_USER = "이미 가입된 유저입니다.";

	public UserException(String message) {
		super(message);
	}
}
