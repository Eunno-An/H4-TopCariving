package com.backend.topcariving.global.auth.repository;

import java.util.Optional;

import com.backend.topcariving.global.auth.entity.AuthInfo;

public interface AuthInfoRepository {

	AuthInfo save(AuthInfo authInfo);
	void deleteById(String refreshToken);
	Optional<AuthInfo> findById(String refreshToken);
}
