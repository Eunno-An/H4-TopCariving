package com.backend.topcariving.global.auth.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth {

	private Long oauthId;

	private String accessToken;

	private String refreshToken;

	private Long expiresIn;

	// FK
	private Long userId;

}
