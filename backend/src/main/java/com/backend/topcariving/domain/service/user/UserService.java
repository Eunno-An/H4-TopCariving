package com.backend.topcariving.domain.service.user;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.domain.exception.UserNotFoundException;
import com.backend.topcariving.domain.repository.user.UserRepository;
import com.backend.topcariving.global.auth.dto.LoginRequestDTO;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.exception.InvalidTokenException;
import com.backend.topcariving.global.auth.repository.AuthInfoRepository;
import com.backend.topcariving.global.auth.service.TokenProvider;

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
			.orElseThrow(UserNotFoundException::new);

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

		updateRefreshToken(userId, authInfo, refreshToken, expiredTime);

		return refreshToken;
	}

	private void updateRefreshToken(Long userId, AuthInfo authInfo, String refreshToken, LocalDateTime expiredTime) {
		if (authInfo.getAuthInfoId() == null || authInfo.getAuthInfoId() == 0) {
			authInfoRepository.save(authInfo);
			return;
		}
		authInfoRepository.update(refreshToken, expiredTime, userId);
	}

	public TokenDTO reissueTokens(final String bearerToken) {
		final String refreshToken = tokenProvider.extractToken(bearerToken);

		final AuthInfo authInfo = authInfoRepository.findByRefreshToken(refreshToken)
			.orElseThrow(InvalidTokenException::new);

		tokenProvider.verifyExpiredTime(authInfo);

		String accessToken = tokenProvider.createAccessToken(authInfo.getUserId());
		String newRefreshToken = createRefreshToken(authInfo.getUserId());

		return new TokenDTO(accessToken, newRefreshToken);
	}

}
