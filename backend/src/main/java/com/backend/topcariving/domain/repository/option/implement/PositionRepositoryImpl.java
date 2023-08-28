package com.backend.topcariving.domain.repository.option.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.dto.archive.response.PositionDTO;
import com.backend.topcariving.domain.entity.option.Position;
import com.backend.topcariving.domain.repository.option.PositionRepository;

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
	public List<PositionDTO> findPositionDTOByCarOptionIds(List<Long> carOptionIds) {

		if (carOptionIds == null || carOptionIds.isEmpty())
			return new ArrayList<>();

		String sql = "SELECT PI.position_id, CO.option_name, PI.left_percent, PI.top_percent "
			+ "FROM POSITION_INFO PI "
			+ "INNER JOIN CAR_OPTION CO ON CO.car_option_id = PI.car_option_id "
			+ "WHERE CO.car_option_id IN (:carOptionIds)";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("carOptionIds", carOptionIds);

		return namedParameterJdbcTemplate.query(sql, paramMap, positionDTORowMapper());
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

	private RowMapper<PositionDTO> positionDTORowMapper() {
		return (rs, rowNum) -> PositionDTO.builder()
			.positionId(rs.getLong("position_id"))
			.optionName(rs.getString("option_name"))
			.leftPercent(rs.getString("left_percent"))
			.topPercent(rs.getString("top_percent"))
			.build();
	}
}
