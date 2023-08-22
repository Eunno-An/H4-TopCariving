package com.backend.topcariving.global.auth.repository.implement;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.repository.AuthInfoRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthInfoRedisRepository implements AuthInfoRepository {

	private final RedisTemplate redisTemplate;

	@Value("${token.refresh-token-expiration}")
	private long REFRESH_TOKEN_EXPIRED_TIME;

	@Override
	public AuthInfo save(AuthInfo authInfo) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(authInfo.getRefreshToken(), authInfo.getUserId());
		redisTemplate.expire(authInfo.getRefreshToken(), REFRESH_TOKEN_EXPIRED_TIME, TimeUnit.MILLISECONDS);
		return authInfo;
	}

	@Override
	public void deleteById(String refreshToken) {
		redisTemplate.opsForValue().getAndDelete(refreshToken);
	}

	@Override
	public Optional<AuthInfo> findById(String refreshToken) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		Long userId = valueOperations.get(refreshToken);

		if (Objects.isNull(userId))
			return Optional.empty();

		return Optional.of(new AuthInfo(refreshToken, userId));
	}
}
