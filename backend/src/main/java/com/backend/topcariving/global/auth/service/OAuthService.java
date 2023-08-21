package com.backend.topcariving.global.auth.service;

import static com.backend.topcariving.global.auth.entity.enums.LoginType.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.domain.exception.AlreadyExistUserException;
import com.backend.topcariving.domain.exception.UserNotFoundException;
import com.backend.topcariving.domain.repository.user.UserRepository;
import com.backend.topcariving.global.auth.dto.OauthInfoDTO;
import com.backend.topcariving.global.auth.dto.OauthLoginDTO;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.entity.Oauth;
import com.backend.topcariving.global.auth.exception.InvalidOauthException;
import com.backend.topcariving.global.auth.repository.AuthInfoRepository;
import com.backend.topcariving.global.auth.repository.OauthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class OAuthService {

	private static final String TOKEN_URL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/oauth2/token";
	private static final String PROFILE_URL = "https://prd.kr-ccapi.hyundai.com/api/v1/user/profile";
	private static final String REDIRECT_URI = "https://dev.topcariving.com/oauth/authorize";

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
		if (oauth.isPresent()) {
			oauthRepository.update(authInfo.getUserId(), token);
			return;
		}
		Oauth newOauth = Oauth.builder()
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.expiresIn(token.getExpiresIn())
			.userId(authInfo.getUserId())
			.build();

		oauthRepository.save(newOauth);
	}

	private OauthLoginDTO getToken(String code) {
		URI uri = generateTokenUri("authorization_code", "code", code);

		ResponseEntity<OauthLoginDTO> exchange = (ResponseEntity<OauthLoginDTO>)doRequest(uri, OauthLoginDTO.class);

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

		if (authInfo.isPresent() && authInfo.get().getLoginType() == LOCAL) {
			throw new AlreadyExistUserException();
		}

		String refreshToken = tokenProvider.createRefreshToken();
		LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION / 1_000);

		if (authInfo.isEmpty()) {
			User user = new User(null, userInfo.getName(), userInfo.getEmail(), null);
			user = userRepository.save(user);
			AuthInfo newAuthInfo = AuthInfo.builder()
				.userId(user.getUserId())
				.loginType(HYUNDAI)
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

	public void logout(Long userId) {
		AuthInfo authInfo = authInfoRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
		if (HYUNDAI == authInfo.getLoginType()) {
			authLogout(userId);
		}

		authInfoRepository.update(null, LocalDateTime.now(), userId);
	}

	private void authLogout(Long userId) {
		Optional<Oauth> findOauth = oauthRepository.findByUserId(userId);
		if (findOauth.isEmpty()) {
			log.info("Oauth에 대한 유저의 정보가 없습니다 DB를 확인해주세요");
			return;
		}
		Oauth oauth = findOauth.get();
		URI uri = generateTokenUri("delete", "access_token", oauth.getAccessToken());

		ResponseEntity<String> exchange = (ResponseEntity<String>) doRequest(uri, String.class);
		HttpStatus code = exchange.getStatusCode();

		if (code.is4xxClientError()) {
			URI refreshUri = generateTokenUri("refresh_token", "refresh_token", oauth.getRefreshToken());
			ResponseEntity<OauthLoginDTO> exchangeOauth = (ResponseEntity<OauthLoginDTO>) doRequest(refreshUri, OauthLoginDTO.class);

			uri = generateTokenUri("delete", "access_token", exchangeOauth.getBody().getAccessToken());
			exchange = (ResponseEntity<String>) doRequest(uri, String.class);
		}

		oauthRepository.deleteByUserId(userId);
	}

	private ResponseEntity<?> doRequest(URI uri, Class<?> clazz) {
		String authorizedValue = clientId + ":" + clientSecret;
		authorizedValue = Base64.getEncoder().encodeToString(authorizedValue.getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Basic " + authorizedValue);
		headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity(headers), clazz);
	}

	private URI generateTokenUri(String grantType, String key, String value) {
		return UriComponentsBuilder
			.fromUriString(TOKEN_URL)
			.queryParam("grant_type", grantType)
			.queryParam(key, value)
			.queryParam("redirect_uri", REDIRECT_URI)
			.encode()
			.build()
			.toUri();
	}
}
