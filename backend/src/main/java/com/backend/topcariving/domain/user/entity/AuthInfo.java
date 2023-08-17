package com.backend.topcariving.domain.user.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthInfo {

	private Long authInfoId;

	private String refreshToken;

	private LocalDateTime expiredTime;

	private String loginType;

	// FK
	private Long userId;

	public void updateToken(String refreshToken, LocalDateTime expiredTime) {
		this.refreshToken = refreshToken;
		this.expiredTime = expiredTime;
	}
}
