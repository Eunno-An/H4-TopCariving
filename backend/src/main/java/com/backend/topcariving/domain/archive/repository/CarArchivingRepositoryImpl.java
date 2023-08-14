package com.backend.topcariving.domain.archive.repository;

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

import com.backend.topcariving.domain.archive.entity.CarArchiving;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CarArchivingRepositoryImpl implements CarArchivingRepository {

	private final JdbcTemplate jdbcTemplate;
	@Override
	public CarArchiving save(CarArchiving carArchiving) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("CAR_ARCHIVING").usingGeneratedKeyColumns("archiving_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("day_time", LocalDateTime.now());
		parameters.put("is_complete", carArchiving.getIsComplete());
		parameters.put("is_alive", carArchiving.getIsAlive());
		parameters.put("archiving_type", carArchiving.getArchivingType());
		parameters.put("user_id", carArchiving.getUserId());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return CarArchiving.builder()
			.archivingId(key.longValue())
			.dayTime(carArchiving.getDayTime())
			.isComplete(carArchiving.getIsComplete())
			.isAlive(carArchiving.getIsAlive())
			.archivingType(carArchiving.getArchivingType())
			.userId(carArchiving.getUserId())
			.build();
	}

	@Override
	public boolean existsByUserIdAndArchivingId(Long userId, Long archivingId) {
		String sql = "SELECT * FROM CAR_ARCHIVING WHERE user_id = ? AND archiving_id = ?;";
		List<CarArchiving> results = jdbcTemplate.query(sql, carArchivingRowMapper(), userId, archivingId);
		return !results.isEmpty();
	}

	@Override
	public void updateIsCompleteByArchivingId(final Long archivingId, final Boolean isComplete) {
		String sql = "UPDATE CAR_ARCHIVING SET is_complete = ? WHERE archiving_id = ?";

		jdbcTemplate.update(sql, isComplete, archivingId);
	}

	@Override
	public Optional<CarArchiving> findById(final Long archivingId) {
		String sql = "SELECT * FROM CAR_ARCHIVING WHERE archiving_id = ?";

		final List<CarArchiving> results = jdbcTemplate.query(sql, carArchivingRowMapper(), archivingId);

		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	private RowMapper<CarArchiving> carArchivingRowMapper() {
		return (rs, rowNum) -> {
			CarArchiving carArchiving = CarArchiving.builder()
				.archivingId(rs.getLong("archiving_id"))
				.dayTime(rs.getTimestamp("day_time").toLocalDateTime())
				.isComplete(rs.getBoolean("is_complete"))
				.isAlive(rs.getBoolean("is_alive"))
				.archivingType(rs.getString("archiving_type"))
				.userId(rs.getLong("user_id"))
				.build();

			return carArchiving;
		};
	}
}
