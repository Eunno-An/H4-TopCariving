package com.backend.topcariving.global.exception;

public class InvalidOauthException extends RuntimeException {

	private static final String INVALID_OAUTH = "Oauth 인증 오류입니다.";

	public InvalidOauthException() {
		super(INVALID_OAUTH);
	}
}
