package com.backend.topcariving.domain.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.user.dto.LoginRequestDTO;
import com.backend.topcariving.domain.user.service.UserService;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.service.CookieProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="로그인 로직 담당 API")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final CookieProvider cookieProvider;

	@PostMapping("/login")
	@ApiResponse(responseCode = "200", description = "성공하면, Authorization 헤더에 access-token 값 반환")
	@Operation(summary = "로그인", description = "로그인을 수행하고, Authorization 헤더에 access-token 값을 반환한다")
	public ResponseEntity<Void> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		final TokenDTO token = userService.login(loginRequestDTO);

		final ResponseCookie accessToken = cookieProvider.createAccessTokenCookie(token.getAccessToken());
		final ResponseCookie refreshToken = cookieProvider.createRefreshTokenCookie(token.getRefreshToken());
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, accessToken.toString(), refreshToken.toString())
			.build();
	}

	@GetMapping("/reissue")
	public ResponseEntity<Void> reissue(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String bearerToken) {
		String accessToken = userService.reissueAccessToken(bearerToken);
		final ResponseCookie accessTokenCookie = cookieProvider.createAccessTokenCookie(accessToken);
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
			.build();
	}
}
