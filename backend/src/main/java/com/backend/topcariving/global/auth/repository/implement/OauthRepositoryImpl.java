package com.backend.topcariving.global.auth.repository.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.global.auth.entity.Oauth;
import com.backend.topcariving.global.auth.dto.OauthLoginDTO;
import com.backend.topcariving.global.auth.repository.OauthRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OauthRepositoryImpl implements OauthRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Oauth save(Oauth oauth) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("OAUTH").usingGeneratedKeyColumns("oauth_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("access_token", oauth.getAccessToken());
		parameters.put("refresh_token", oauth.getRefreshToken());
		parameters.put("expires_in", oauth.getExpiresIn());
		parameters.put("user_id", oauth.getUserId());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return Oauth.builder()
			.oauthId(key.longValue())
			.accessToken(oauth.getAccessToken())
			.refreshToken(oauth.getRefreshToken())
			.expiresIn(oauth.getExpiresIn())
			.userId(oauth.getUserId())
			.build();
	}

	@Override
	public void update(Long userId, OauthLoginDTO oauthLoginDTO) {
		String sql = "UPDATE OAUTH SET access_token = ?, refresh_token = ?, expires_in = ? WHERE user_id = ?;";

		jdbcTemplate.update(sql, oauthLoginDTO.getAccessToken(), oauthLoginDTO.getRefreshToken(),
			oauthLoginDTO.getExpiresIn(), userId);
	}

	@Override
	public Optional<Oauth> findByUserId(Long userId) {
		String sql = "SELECT * FROM OAUTH WHERE user_id = ?;";

		List<Oauth> results = jdbcTemplate.query(sql, oauthRowMapper(), userId);
		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public Optional<Oauth> findByEmail(String email) {
		String sql = "SELECT * FROM OAUTH OA, USER_INFO UI "
			+ "WHERE OA.user_id = UI.user_id AND UI.email = ?;";

		List<Oauth> results = jdbcTemplate.query(sql, oauthRowMapper(), email);
		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public void deleteByUserId(Long userId) {
		String sql = "DELETE FROM OAUTH WHERE user_id = ?;";

		jdbcTemplate.update(sql, userId);
	}

	private RowMapper<Oauth> oauthRowMapper() {
		return (rs, rowNum) -> Oauth.builder()
			.oauthId(rs.getLong("oauth_id"))
			.accessToken(rs.getString("access_token"))
			.refreshToken(rs.getString("refresh_token"))
			.expiresIn(rs.getLong("expires_in"))
			.userId(rs.getLong("user_id"))
			.build();
	}
}
