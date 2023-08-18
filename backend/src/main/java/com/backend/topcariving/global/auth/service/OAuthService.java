package com.backend.topcariving.global.auth.service;

import static com.backend.topcariving.domain.user.entity.LoginType.*;
import static com.backend.topcariving.domain.user.exception.UserException.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.topcariving.domain.user.entity.AuthInfo;
import com.backend.topcariving.domain.user.entity.Oauth;
import com.backend.topcariving.domain.user.entity.User;
import com.backend.topcariving.domain.user.exception.UserException;
import com.backend.topcariving.domain.user.repository.AuthInfoRepository;
import com.backend.topcariving.domain.user.repository.OauthRepository;
import com.backend.topcariving.domain.user.repository.UserRepository;
import com.backend.topcariving.global.auth.dto.OauthInfoDTO;
import com.backend.topcariving.global.auth.dto.OauthLoginDTO;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.exception.InvalidOauthException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OAuthService {

	private static final String TOKEN_URL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/oauth2/token";
	private static final String PROFILE_URL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/profile";

	private final AuthInfoRepository authInfoRepository;
	private final OauthRepository oauthRepository;

	private final UserRepository userRepository;

	private final TokenProvider tokenProvider;

	@Value("${oauth.hyundai.state}")
	private String hyundaiState;

	@Value("${oauth.hyundai.client-id}")
	private String clientId;

	@Value("${oauth.hyundai.client-secret}")
	private String clientSecret;

	@Value("${token.refresh-token-expiration}")
	private long REFRESH_TOKEN_EXPIRATION;

	public TokenDTO authorize(String state, String code) {
		if (!this.hyundaiState.equals(state)) {
			throw new InvalidOauthException();
		}

		OauthLoginDTO token = getToken(code);
		OauthInfoDTO userInfo = getUserInfo(token);
		AuthInfo authInfo = saveUser(userInfo);

		saveOauth(token, userInfo, authInfo);

		String accessToken = tokenProvider.createAccessToken(authInfo.getUserId());
		String refreshToken = authInfo.getRefreshToken();

		return new TokenDTO(accessToken, refreshToken);
	}

	private void saveOauth(OauthLoginDTO token, OauthInfoDTO userInfo, AuthInfo authInfo) {
		Optional<Oauth> oauth = oauthRepository.findByEmail(userInfo.getEmail());
		if (oauth.isEmpty()) {
			Oauth newOauth = Oauth.builder()
				.accessToken(token.getAccessToken())
				.refreshToken(token.getRefreshToken())
				.expiresIn(token.getExpiresIn())
				.build();

			oauthRepository.save(newOauth);
		} else {
			oauthRepository.update(authInfo.getUserId(), token);
		}
	}

	private OauthLoginDTO getToken(String code) {
		URI uri = UriComponentsBuilder
			.fromUriString(TOKEN_URL)
			.queryParam("grant_type", "authorization_code")
			.queryParam("code", code)
			.queryParam("redirect_uri", "https://dev.topcariving.com/oauth/authorize")
			.encode()
			.build()
			.toUri();

		String authorizedValue = clientId + ":" + clientSecret;
		authorizedValue = Base64.getEncoder().encodeToString(authorizedValue.getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Basic " + authorizedValue);
		headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OauthLoginDTO> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity(headers),
			OauthLoginDTO.class);

		return exchange.getBody();
	}

	private OauthInfoDTO getUserInfo(OauthLoginDTO loginDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + loginDTO.getAccessToken());

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OauthInfoDTO> exchange = restTemplate.exchange(PROFILE_URL, HttpMethod.GET,
			new HttpEntity<>(headers), OauthInfoDTO.class);

		return exchange.getBody();
	}

	private AuthInfo saveUser(OauthInfoDTO userInfo) {
		Optional<AuthInfo> authInfo = authInfoRepository.findByEmail(userInfo.getEmail());

		if (authInfo.isPresent() && Objects.equals(authInfo.get().getLoginType(), LOCAL.getType())) {
			throw new UserException(ALREADY_EXIST_USER);
		}

		String refreshToken = tokenProvider.createRefreshToken();
		LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION / 1_000);

		if (authInfo.isEmpty()) {
			User user = new User(null, userInfo.getName(), userInfo.getEmail(), null);
			user = userRepository.save(user);
			AuthInfo newAuthInfo = AuthInfo.builder()
				.userId(user.getUserId())
				.loginType(HYUNDAI.getType())
				.refreshToken(refreshToken)
				.expiredTime(expiredTime)
				.build();
			return authInfoRepository.save(newAuthInfo);
		}

		AuthInfo result = authInfo.get();
		authInfoRepository.update(refreshToken, expiredTime, result.getUserId());
		result.updateToken(refreshToken, expiredTime);
		return result;
	}

}
