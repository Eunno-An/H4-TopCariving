package com.backend.topcariving.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.topcariving.domain.user.dto.LoginRequestDTO;
import com.backend.topcariving.domain.user.entity.AuthInfo;
import com.backend.topcariving.domain.user.entity.User;
import com.backend.topcariving.domain.user.exception.UserException;
import com.backend.topcariving.domain.user.repository.AuthInfoRepository;
import com.backend.topcariving.domain.user.repository.UserRepository;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.backend.topcariving.global.exception.InvalidTokenException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final AuthInfoRepository authInfoRepository;

	@Value("${token.refresh-token-expiration}")
	private long refreshTokenExpiredTime;

	public TokenDTO login(LoginRequestDTO loginRequestDTO) {
		final User user = userRepository.findByEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
			.orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));

		final String accessToken = tokenProvider.createAccessToken(user.getUserId());
		final String refreshToken = createRefreshToken(user.getUserId());

		return new TokenDTO(accessToken, refreshToken);
	}

	private String createRefreshToken(Long userId) {
		final AuthInfo authInfo = authInfoRepository.findByUserId(userId)
			.orElseGet(() -> AuthInfo.builder()
				.userId(userId)
				.build()
			);

		final String refreshToken = tokenProvider.createRefreshToken();
		final LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(refreshTokenExpiredTime / 1_000);
		authInfo.updateToken(refreshToken, expiredTime);

		authInfoRepository.save(authInfo);

		return refreshToken;
	}

	public String reissueAccessToken(final String bearerToken) {
		final String refreshToken = tokenProvider.extractToken(bearerToken);

		final AuthInfo authInfo = authInfoRepository.findByRefreshToken(refreshToken)
			.orElseThrow(InvalidTokenException::new);

		tokenProvider.verifyExpiredTime(authInfo);

		return tokenProvider.createAccessToken(authInfo.getUserId());
	}

}
