package com.backend.topcariving.domain.repository.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.entity.user.User;
import com.backend.topcariving.global.auth.entity.enums.LoginType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public User save(User user) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("USER_INFO").usingGeneratedKeyColumns("user_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", user.getName());
		parameters.put("email", user.getEmail());
		parameters.put("password", user.getPassword());
		parameters.put("login_type", user.getLoginType().getName());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return new User(key.longValue(), user.getName(), user.getEmail(), user.getPassword(), user.getLoginType());
	}

	@Override
	public Optional<User> findById(Long userId) {
		String sql = "SELECT * FROM USER_INFO WHERE user_id = ?";

		List<User> result = jdbcTemplate.query(sql, userRowMapper(), userId);

		if (result.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public Optional<User> findByEmailAndPassword(String email, String password) {
		String sql = "SELECT * FROM USER_INFO WHERE email = ? AND password = ?";

		List<User> result = jdbcTemplate.query(sql, userRowMapper(), email, password);

		if (result.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		String sql = "SELECT * FROM USER_INFO WHERE email = ?";

		List<User> result = jdbcTemplate.query(sql, userRowMapper(), email);

		if (result.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(result.get(0));
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> new User(
			rs.getLong("user_id"),
			rs.getString("name"),
			rs.getString("email"),
			rs.getString("password"),
			LoginType.valueOfName(rs.getString("login_type"))
		);
	}
}
