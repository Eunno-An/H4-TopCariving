package com.backend.topcariving.domain.user.repository;

import java.util.Optional;

import com.backend.topcariving.domain.user.entity.Oauth;
import com.backend.topcariving.global.auth.dto.OauthLoginDTO;

public interface OauthRepository {

	Oauth save(Oauth oauth);

	void update(Long userId, OauthLoginDTO oauthLoginDTO);

	Optional<Oauth> findByUserId(Long userId);

	Optional<Oauth> findByEmail(String email);

	void deleteByUserId(Long userId);
}
