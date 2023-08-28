package com.backend.topcariving.domain.service.user;

import static org.mockito.BDDMockito.*;

import java.util.Objects;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.exception.AlreadyExistUserException;
import com.backend.topcariving.domain.exception.UserNotFoundException;
import com.backend.topcariving.domain.service.user.UserService;
import com.backend.topcariving.global.auth.dto.LoginRequestDTO;
import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.entity.enums.LoginType;
import com.backend.topcariving.global.auth.repository.AuthInfoRepository;
import com.backend.topcariving.domain.repository.user.UserRepository;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.service.TokenProvider;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends TestSupport {

	@Mock
	private UserRepository userRepository;

	@Mock
	private TokenProvider tokenProvider;

	@Mock
	private AuthInfoRepository authInfoRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void 로그인_시_액세스_토큰이_발급되어야_한다() {
		// given
		final User user = new User(1L, "이름", "이메일", "패스워드", LoginType.LOCAL);
		given(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
			.willReturn(Optional.of(user));

		final LoginRequestDTO loginRequestDTO = new LoginRequestDTO("이메일", "패스워드");

		String accessToken = "1234";
		given(tokenProvider.createAccessToken(user.getUserId()))
			.willReturn(accessToken);
		String refreshToken = "5678";
		given(tokenProvider.createRefreshToken())
			.willReturn(refreshToken);

		given(authInfoRepository.save(any(AuthInfo.class)))
			.willReturn(null);


		// when
		final TokenDTO token = userService.login(loginRequestDTO);

		// then
		Assertions.assertThat(accessToken).isEqualTo(token.getAccessToken());
		Assertions.assertThat(refreshToken).isEqualTo(token.getRefreshToken());
	}

	@Test
	void 현대_로그인_타입으로_정보가_존재한다면_예외를_던져야_한다() {
		// given
		User user = new User(1L, "이름", "이메일", "패스워드", LoginType.HYUNDAI);
		given(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
			.willReturn(Optional.of(user));

		LoginRequestDTO loginRequestDTO = new LoginRequestDTO("이메일", "패스워드");

		// when, then
		Assertions.assertThatThrownBy(() -> {
			userService.login(loginRequestDTO);
		}).isInstanceOf(AlreadyExistUserException.class);
	}

	@Test
	void 유저_정보가_없을_경우_예외를_던져야_한다() {
		// given
		final User user = new User(1L, "이름", "이메일", "패스워드", LoginType.LOCAL);
		given(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()))
			.willReturn(Optional.empty());

		final LoginRequestDTO loginRequestDTO = new LoginRequestDTO("이메일", "패스워드");

		// when, then
		Assertions.assertThatThrownBy(() -> {
			userService.login(loginRequestDTO);
		}).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void reissue_를_수행해야한다() {
		// given
		String bearerToken = "Bearer refreshToken";
		String refreshToken = "refreshToken";
		Long userId = 1L;
		AuthInfo authInfo = new AuthInfo(refreshToken, userId);

		given(authInfoRepository.findById(refreshToken)).willReturn(Optional.of(authInfo));
		given(tokenProvider.extractToken(bearerToken)).willReturn(refreshToken);

		// when
		TokenDTO tokenDTO = userService.reissueTokens(bearerToken);

		// then
		softAssertions.assertThat(Objects.equals(tokenDTO.getRefreshToken(), refreshToken)).isFalse();
	}

}