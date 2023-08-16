package com.backend.topcariving.domain.user.repository;

import java.util.Optional;

import com.backend.topcariving.domain.user.entity.User;

public interface UserRepository {

	User save(User user);
	Optional<User> findById(Long userId);
	Optional<User> findByEmailAndPassword(String email, String password);
}
