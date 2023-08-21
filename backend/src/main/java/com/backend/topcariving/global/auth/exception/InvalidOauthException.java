package com.backend.topcariving.global.auth.exception;

import com.backend.topcariving.global.exception.ExceptionStatus;

public class InvalidOauthException extends RuntimeException {

	public InvalidOauthException() {
		super(ExceptionStatus.INVALID_OAUTH.getMessage());
	}
}
