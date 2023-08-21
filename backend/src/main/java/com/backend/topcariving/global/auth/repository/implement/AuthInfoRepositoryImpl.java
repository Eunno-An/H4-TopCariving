package com.backend.topcariving.global.auth.repository.implement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.global.auth.entity.AuthInfo;
import com.backend.topcariving.global.auth.repository.AuthInfoRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthInfoRepositoryImpl implements AuthInfoRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public AuthInfo save(AuthInfo authInfo) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("AUTH_INFO").usingGeneratedKeyColumns("auth_info_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("refresh_token", authInfo.getRefreshToken());
		parameters.put("expired_time", authInfo.getExpiredTime());
		parameters.put("user_id", authInfo.getUserId());
		parameters.put("login_type", authInfo.getLoginType().getName());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return AuthInfo.builder()
			.authInfoId(key.longValue())
			.refreshToken(authInfo.getRefreshToken())
			.expiredTime(authInfo.getExpiredTime())
			.userId(authInfo.getUserId())
			.loginType(authInfo.getLoginType())
			.build();
	}

	@Override
	public void update(String refreshToken, LocalDateTime expiredTime, Long userId) {
		String sql = "UPDATE AUTH_INFO SET refresh_token = ?, expired_time = ? WHERE user_id = ?;";

		jdbcTemplate.update(sql, refreshToken, Timestamp.valueOf(expiredTime), userId);
	}

	@Override
	public Optional<AuthInfo> findByUserId(Long userId) {
		String sql = "SELECT * FROM AUTH_INFO WHERE user_id = ?";

		List<AuthInfo> result = jdbcTemplate.query(sql, authInfoRowMapper(), userId);

		if (result.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public Optional<AuthInfo> findByEmail(String email) {
		String sql = "SELECT * FROM AUTH_INFO AI, USER_INFO UI "
			+ "WHERE AI.user_id = UI.user_id "
			+ "AND UI.email = ?;";

		List<AuthInfo> result = jdbcTemplate.query(sql, authInfoRowMapper(), email);
		if(result.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public void deleteById(Long authInfoId) {
		String sql = "DELETE FROM AUTH_INFO WHERE auth_info_id = ?;";
		jdbcTemplate.update(sql, authInfoId);
	}

	@Override
	public Optional<AuthInfo> findByRefreshToken(String refreshToken) {
		String sql = "SELECT * FROM AUTH_INFO WHERE refresh_token = ?;";
		List<AuthInfo> results = jdbcTemplate.query(sql, authInfoRowMapper(), refreshToken);
		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	private RowMapper<AuthInfo> authInfoRowMapper() {
		return (rs, rowNum) -> AuthInfo.builder()
			.authInfoId(rs.getLong("auth_info_id"))
			.refreshToken(rs.getString("refresh_token"))
			.expiredTime(rs.getTimestamp("expired_time").toLocalDateTime())
			.userId(rs.getLong("user_id"))
			.build();
	}
}
