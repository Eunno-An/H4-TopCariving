package com.backend.topcariving.global.auth.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.backend.topcariving.domain.user.entity.AuthInfo;
import com.backend.topcariving.domain.user.repository.AuthInfoRepository;
import com.backend.topcariving.global.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {

	@Value("${token.access-token-expiration}")
	private long ACCESS_TOKEN_EXPIRATION;

	@Value("${token.secret-key}")
	private String SECRET_KEY;

	private static final String BEARER_TYPE = "Bearer";

	private AuthInfoRepository authInfoRepository;

	public String createAccessToken(Long userId) {
		final byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
		final SecretKey key = Keys.hmacShaKeyFor(keyByte);

		final Date now = new Date();
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
			.setIssuer("TopCariving")
			.setSubject(Long.toString(userId))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken() {
		return UUID.randomUUID().toString();
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Long getUserIdFromToken(String token) {
		return Long.parseLong(getAllClaims(token).getSubject());
	}

	public String extractToken(String bearerToken) {
		if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_TYPE)) {
			throw new InvalidTokenException();
		}
		return bearerToken.substring(BEARER_TYPE.length() + 1);
	}

	public void verifyToken(final String token) {
		Jwts
			.parserBuilder()
			.setSigningKey(SECRET_KEY)
			.build()
			.parseClaimsJws(token);
	}

	public void verifyExpiredTime(AuthInfo authInfo) {
		final LocalDateTime expiredTime = authInfo.getExpiredTime();
		if (expiredTime.isBefore(LocalDateTime.now())) {
			authInfoRepository.deleteById(authInfo.getAuthInfoId());
			throw new InvalidTokenException();
		}
	}
}
