package com.backend.topcariving.domain.option.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.Position;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Position> findByCarOptionId(Long carOptionId) {
		String sql = "SELECT * FROM POSITION_INFO WHERE car_option_id = ?;";
		List<Position> results = jdbcTemplate.query(sql, positionRowMapper(), carOptionId);
		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public List<Position> findByCarOptionIds(List<Long> carOptionIds) {
		String sql = "SELECT * FROM POSITION_INFO WHERE car_option_id IN (:carOptionIds)";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("carOptionIds", carOptionIds);

		return namedParameterJdbcTemplate.query(sql, paramMap, positionRowMapper());
	}

	private RowMapper<Position> positionRowMapper() {
		return (rs, rowNum) -> new Position(
			rs.getLong("position_id"),
			rs.getString("left_pixel"),
			rs.getString("top_pixel"),
			rs.getString("left_percent"),
			rs.getString("top_percent"),
			rs.getLong("car_option_id")
		);
	}
}
