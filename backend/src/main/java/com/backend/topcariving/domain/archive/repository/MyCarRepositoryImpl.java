package com.backend.topcariving.domain.archive.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.archive.entity.MyCar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MyCarRepositoryImpl implements MyCarRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public MyCar save(MyCar myCar) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("MY_CAR").usingGeneratedKeyColumns("my_car_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("car_option_id", myCar.getCarOptionId());
		parameters.put("archiving_id", myCar.getArchivingId());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return MyCar.builder()
			.myCarId(key.longValue())
			.carOptionId(myCar.getCarOptionId())
			.archivingId(myCar.getArchivingId())
			.build();
	}

	@Override
	public Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId) {
		String sql = "SELECT * FROM MY_CAR WHERE archiving_id = ? AND car_option_id = ?;";
		List<MyCar> results = jdbcTemplate.query(sql, myCarRowMapper(), archivingId, carOptionId);
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public List<MyCar> findByArchivingId(Long archivingId) {
		String sql = "SELECT * FROM MY_CAR WHERE archiving_id = ?;";
		return jdbcTemplate.query(sql, myCarRowMapper(), archivingId);
	}

	private RowMapper<MyCar> myCarRowMapper() {
		return (rs, rowNum) -> {
			MyCar myCar = MyCar.builder()
				.myCarId(rs.getLong("my_car_id"))
				.carOptionId(rs.getLong("car_option_id"))
				.archivingId(rs.getLong("archiving_id"))
				.build();

			return myCar;
		};
	}
}
