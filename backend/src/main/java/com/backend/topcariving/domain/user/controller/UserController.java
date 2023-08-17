package com.backend.topcariving.domain.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.user.dto.LoginRequestDTO;
import com.backend.topcariving.domain.user.service.UserService;
import com.backend.topcariving.global.auth.dto.TokenDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="로그인 로직 담당 API")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/login")
	@ApiResponse(responseCode = "200", description = "성공하면, Authorization 헤더에 access-token 값 반환")
	@Operation(summary = "로그인", description = "로그인을 수행하고, Authorization 헤더에 access-token 값을 반환한다")
	public TokenDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return userService.login(loginRequestDTO);
	}

	@GetMapping("/reissue")
	@SecurityRequirement(name = "Authorization")
	public String reissue(@Parameter(hidden = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String bearerToken) {
		return userService.reissueAccessToken(bearerToken);
	}
}
