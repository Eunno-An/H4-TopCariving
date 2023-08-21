package com.backend.topcariving.global.auth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.backend.topcariving.global.auth.entity.AuthInfo;

public interface AuthInfoRepository {

	AuthInfo save(AuthInfo authInfo);

	void update(String refreshToken, LocalDateTime expiredTime, Long userId);

	Optional<AuthInfo> findByUserId(Long userId);

	Optional<AuthInfo> findByEmail(String email);

	void deleteById(Long authInfoId);

	Optional<AuthInfo> findByRefreshToken(String refreshToken);
}
