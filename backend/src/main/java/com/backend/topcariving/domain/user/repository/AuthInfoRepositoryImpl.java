package com.backend.topcariving.domain.user.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.user.entity.AuthInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthInfoRepositoryImpl implements AuthInfoRepository {

	private final JdbcTemplate jdbcTemplate;

	// TODO: Update하는거랑 구별해서 꼭 쓰기
	@Override
	public AuthInfo save(AuthInfo authInfo) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("AUTH_INFO").usingGeneratedKeyColumns("auth_info_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("refresh_token", authInfo.getRefreshToken());
		parameters.put("expired_time", authInfo.getExpiredTime());
		parameters.put("user_id", authInfo.getUserId());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return AuthInfo.builder()
			.authInfoId(key.longValue())
			.refreshToken(authInfo.getRefreshToken())
			.expiredTime(authInfo.getExpiredTime())
			.userId(authInfo.getUserId())
			.build();
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
