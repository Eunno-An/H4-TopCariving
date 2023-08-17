package com.backend.topcariving.global.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

	public static final String ACCESS_TOKEN_NAME = "accessToken";

	public static final String REFRESH_TOKEN_NAME = "refreshToken";

	@Value("${token.access-token-expiration}")
	private long ACCESS_TOKEN_EXPIRED_TIME;

	@Value("${token.refresh-token-expiration}")
	private long REFRESH_TOKEN_EXPIRED_TIME;

	public ResponseCookie createAccessTokenCookie(final String token) {
		return ResponseCookie.from(ACCESS_TOKEN_NAME, token)
			.path("/")
			.maxAge(ACCESS_TOKEN_EXPIRED_TIME)
			.build();
	}

	public ResponseCookie createRefreshTokenCookie(final String token) {
		return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
			.path("/")
			.maxAge(REFRESH_TOKEN_EXPIRED_TIME)
			.build();
	}

}
