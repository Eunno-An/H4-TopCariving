package com.backend.topcariving.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자의 이메일과 비밀번호를 가지는 Login Request DTO")
@AllArgsConstructor
@Getter
public class LoginRequestDTO {

	@Schema(description = "사용자 이메일", example = "mg@gmail.com")
	private String email;

	@Schema(description = "사용자 비밀번호", example = "1234")
	private String password;
}
