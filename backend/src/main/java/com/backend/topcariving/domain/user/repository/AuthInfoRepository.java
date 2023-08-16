package com.backend.topcariving.domain.user.repository;

import java.util.Optional;

import com.backend.topcariving.domain.user.entity.AuthInfo;

public interface AuthInfoRepository {

	AuthInfo save(AuthInfo authInfo);
	Optional<AuthInfo> findByUserId(Long userId);

	void deleteById(Long authInfoId);

	Optional<AuthInfo> findByRefreshToken(String refreshToken);
}
