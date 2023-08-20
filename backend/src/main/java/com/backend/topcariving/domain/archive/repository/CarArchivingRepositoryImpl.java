package com.backend.topcariving.domain.archive.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.archive.dto.CarDTO;
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
	public boolean existsByArchivingId(Long archivingId) {
		String sql = "SELECT * FROM CAR_ARCHIVING WHERE archiving_id = ?;";
		List<CarArchiving> results = jdbcTemplate.query(sql, carArchivingRowMapper(), archivingId);
		return !results.isEmpty();
	}

	@Override
	public void updateIsCompleteByArchivingId(final Long archivingId, final Boolean isComplete) {
		String sql = "UPDATE CAR_ARCHIVING SET is_complete = ? WHERE archiving_id = ?";

		jdbcTemplate.update(sql, isComplete, archivingId);
	}

	@Override
	public void updateIsAliveByArchivingId(Boolean isAlive, Long archivingId) {
		String sql = "UPDATE CAR_ARCHIVING SET is_alive = ? WHERE archiving_id = ?;";

		jdbcTemplate.update(sql, isAlive, archivingId);
	}

	@Override
	public Optional<CarArchiving> findById(final Long archivingId) {
		String sql = "SELECT * FROM CAR_ARCHIVING WHERE archiving_id = ?";

		final List<CarArchiving> results = jdbcTemplate.query(sql, carArchivingRowMapper(), archivingId);

		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public List<CarArchiving> findByCarOptionIdsAndArchivingTypes(List<Long> carOptionIds, List<String> archivingTypes, Integer pageNumber, Integer pageSize) {
		String sql = "SELECT * FROM CAR_ARCHIVING "
			+ "WHERE archiving_id IN "
			+ "(SELECT archiving_id FROM MY_CAR "
			+ "WHERE car_option_id IN (:carOptionIds) "
			+ "GROUP BY archiving_id "
			+ "HAVING COUNT(DISTINCT car_option_id) = (:carOptionsSize)) "
			+ "AND archiving_type IN (:archivingTypes) "
			+ "AND is_alive = true "
			+ "ORDER BY day_time DESC "
			+ "LIMIT :pageSize "
			+ "OFFSET :offset;";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("carOptionIds", carOptionIds);
		paramMap.put("carOptionsSize", carOptionIds.size());
		paramMap.put("archivingTypes", archivingTypes);
		paramMap.put("pageSize", pageSize);
		paramMap.put("offset", (pageNumber - 1) * pageSize);

		return namedParameterJdbcTemplate.query(sql, paramMap, carArchivingRowMapper());
	}

	@Override
	public List<CarArchiving> findByArchivingTypes(List<String> archivingTypes, Integer pageNumber, Integer pageSize) {
		String sql = "SELECT * FROM CAR_ARCHIVING WHERE archiving_type IN (:archivingTypes) "
			+ "AND is_alive = true "
			+ "ORDER BY day_time DESC "
			+ "LIMIT :pageSize OFFSET :offset;";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("archivingTypes", archivingTypes);
		paramMap.put("pageSize", pageSize);
		paramMap.put("offset", (pageNumber - 1) * pageSize);

		return namedParameterJdbcTemplate.query(sql, paramMap, carArchivingRowMapper());
	}

	@Override
	public List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSize(Long userId, Integer pageNumber, Integer pageSize) {

		String sql = "SELECT * FROM CAR_ARCHIVING AS CA "
			+ "INNER JOIN MY_CAR AS MC ON CA.archiving_id = MC.archiving_id "
			+ "INNER JOIN CAR_OPTION AS CO ON MC.car_option_id = CO.car_option_id "
			+ "WHERE CA.archiving_id IN "
			+ "(SELECT archiving_id FROM CAR_ARCHIVING WHERE user_id = ? AND is_alive = true  "
			+ "ORDER BY day_time DESC "
			+ "LIMIT ? "
			+ "OFFSET ?)";
		try {
			return jdbcTemplate.queryForObject(sql, new CarDTORowMapper(), userId, pageSize, (pageNumber - 1) * pageSize);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrue(Long userId, Integer pageNumber,
		Integer pageSize) {
		String sql = "SELECT * FROM CAR_ARCHIVING AS CA "
			+ "INNER JOIN MY_CAR AS MC ON CA.archiving_id = MC.archiving_id "
			+ "INNER JOIN CAR_OPTION AS CO ON MC.car_option_id = CO.car_option_id "
			+ "WHERE CA.archiving_id IN "
			+ "(SELECT ARC.archiving_id FROM CAR_ARCHIVING ARC "
			+ "JOIN BOOKMARK BM ON ARC.archiving_id = BM.archiving_id "
			+ "WHERE BM.user_id = ? AND ARC.is_alive = true AND BM.is_alive = true "
			+ "ORDER BY ARC.day_time DESC "
			+ "LIMIT ? "
			+ "OFFSET ?)";
		try {
			return jdbcTemplate.queryForObject(sql, new CarDTORowMapper(), userId, pageSize, (pageNumber - 1) * pageSize);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}

	private RowMapper<CarArchiving> carArchivingRowMapper() {
		return (rs, rowNum) -> CarArchiving.builder()
			.archivingId(rs.getLong("archiving_id"))
			.dayTime(rs.getTimestamp("day_time").toLocalDateTime())
			.isComplete(rs.getBoolean("is_complete"))
			.isAlive(rs.getBoolean("is_alive"))
			.archivingType(rs.getString("archiving_type"))
			.userId(rs.getLong("user_id"))
			.build();
	}
}
