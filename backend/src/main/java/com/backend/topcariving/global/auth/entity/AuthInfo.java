package com.backend.topcariving.global.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthInfo {

	private String refreshToken;
	private Long userId;
}
