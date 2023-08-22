package com.backend.topcariving.domain.repository.user;

import java.util.Optional;

import com.backend.topcariving.domain.entity.user.User;

public interface UserRepository {

	User save(User user);
	Optional<User> findById(Long userId);
	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByEmail(String email);
}
