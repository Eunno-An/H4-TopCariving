package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.EngineDetail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class EngineDetailRepositoryImpl implements EngineDetailRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<EngineDetail> findByCarOptionId(Long carOptionId) {
		String sql = "SELECT * FROM ENGINE_DETAIL WHERE car_option_id = ?";
		List<EngineDetail> results = jdbcTemplate.query(sql, engineDetailRowMapper(), carOptionId);

		return Optional.ofNullable(results.get(0));
	}
	
	private RowMapper<EngineDetail> engineDetailRowMapper() {
		return (rs, rowNum) -> {
			EngineDetail engineDetail = new EngineDetail(
				rs.getLong("engine_detail_id"),
				rs.getString("max_output"),
				rs.getString("max_torque"),
				rs.getLong("car_option_id")
			);
			return engineDetail;
		};
	}

}
