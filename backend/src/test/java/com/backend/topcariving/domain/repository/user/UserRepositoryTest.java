package com.backend.topcariving.domain.repository.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.global.auth.entity.enums.LoginType;

@JdbcTest
class UserRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository = new UserRepositoryImpl(jdbcTemplate);
	}

	@Test
	void save() {
		// given
		Long userId = 11L;
		String name = "name";
		String email = "email";
		String password = "password";
		LoginType loginType = LoginType.LOCAL;
		User createdUser = new User(userId, name, email, password, loginType);

		// when
		User user = userRepository.save(createdUser);

		// then
		softAssertions.assertThat(user.getUserId()).as("userId는 11L이어야 함").isEqualTo(11L);
		softAssertions.assertThat(user.getName()).as("name은 'name'이어야 함").isEqualTo("name");
		softAssertions.assertThat(user.getEmail()).as("email은 'email'이어야 함").isEqualTo("email");
		softAssertions.assertThat(user.getLoginType()).as("로그인 타입은 LOCAL이어야 함").isEqualTo(LoginType.LOCAL);
	}

	@Test
	void findById() {
		// given
		Long userId = 1L;

		// when
		Optional<User> findUser = userRepository.findById(userId);

		// then
		Assertions.assertThat(findUser.get().getUserId()).isEqualTo(userId);
	}

	@Test
	void findByIdEmpty() {
		// given
		Long userId = 11L;

		// when
		Optional<User> findUser = userRepository.findById(userId);

		// then
		Assertions.assertThat(findUser).isEmpty();
	}

	@Test
	void findByEmailAndPassword() {
		// given
		String email = "mg@gmail.com";
		String password = "1234";

		// when
		Optional<User> findUser = userRepository.findByEmailAndPassword(email, password);

		// then
		User user = findUser.get();
		softAssertions.assertThat(user.getUserId()).as("userId는 1이어야 함").isEqualTo(1L);
		softAssertions.assertThat(user.getName()).as("name은 '김민구'여야 함").isEqualTo("김민구");
		softAssertions.assertThat(user.getEmail()).as("email은 'mg@gmail.com'이어야 함").isEqualTo("mg@gmail.com");
	}

	@Test
	void findByEmailAndPasswordEmpty() {
		// given
		String email = "best@mail.com";
		String password = "1234";

		// when
		Optional<User> findUser = userRepository.findByEmailAndPassword(email, password);

		// then
		Assertions.assertThat(findUser).isEmpty();
	}

	@Test
	void findByEmail() {
		// given
		String email = "mg@gmail.com";

		// when
		Optional<User> findUser = userRepository.findByEmail(email);

		// then
		User user = findUser.get();
		softAssertions.assertThat(user.getUserId()).as("userId는 1이어야 함").isEqualTo(1L);
		softAssertions.assertThat(user.getName()).as("name은 '김민구'여야 함").isEqualTo("김민구");
		softAssertions.assertThat(user.getEmail()).as("email은 'mg@gmail.com'이어야 함").isEqualTo("mg@gmail.com");
	}

	@Test
	void findByEmailEmpty() {
		// given
		String email = "good@mail.com";

		// when
		Optional<User> findUser = userRepository.findByEmail(email);

		// then
		Assertions.assertThat(findUser).isEmpty();
	}

}