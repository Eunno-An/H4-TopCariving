package com.backend.topcariving.domain.service.user;

import org.springframework.stereotype.Service;

import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.domain.exception.AlreadyExistUserException;
import com.backend.topcariving.domain.exception.UserNotFoundException;
import com.backend.topcariving.domain.repository.user.UserRepository;
import com.backend.topcariving.global.auth.dto.LoginRequestDTO;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.entity.enums.LoginType;
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

	public TokenDTO login(LoginRequestDTO loginRequestDTO) {
		final User user = userRepository.findByEmailAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
			.orElseThrow(UserNotFoundException::new);

		if (user.getLoginType() == LoginType.HYUNDAI) {
			throw new AlreadyExistUserException();
		}

		final String accessToken = tokenProvider.createAccessToken(user.getUserId());
		final String refreshToken = createRefreshToken(user.getUserId());

		return new TokenDTO(accessToken, refreshToken);
	}

	private String createRefreshToken(Long userId) {
		final String refreshToken = tokenProvider.createRefreshToken();
		authInfoRepository.save(new AuthInfo(refreshToken, userId));
		return refreshToken;
	}

	public TokenDTO reissueTokens(final String bearerToken) {
		final String refreshToken = tokenProvider.extractToken(bearerToken);

		final AuthInfo authInfo = authInfoRepository.findById(refreshToken)
			.orElseThrow(InvalidTokenException::new);

		authInfoRepository.deleteById(authInfo.getRefreshToken());
		String accessToken = tokenProvider.createAccessToken(authInfo.getUserId());
		String newRefreshToken = createRefreshToken(authInfo.getUserId());

		return new TokenDTO(accessToken, newRefreshToken);
	}

}
