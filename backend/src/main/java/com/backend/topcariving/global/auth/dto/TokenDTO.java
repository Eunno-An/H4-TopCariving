package com.backend.topcariving.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDTO {

	private String accessToken;

	private String refreshToken;
}
