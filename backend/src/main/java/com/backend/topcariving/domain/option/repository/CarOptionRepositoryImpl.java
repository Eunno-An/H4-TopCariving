package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.CarOption;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CarOptionRepositoryImpl implements CarOptionRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<CarOption> findByCarOptionId(Long carOptionId) {
		String sql = "SELECT * FROM CAR_OPTION WHERE car_option_id = ?;";
		List<CarOption> results = jdbcTemplate.query(sql, carOptionRowMapper(), carOptionId);
		return Optional.ofNullable(results.get(0));
	}

	@Override
	public List<CarOption> findByParentOptionId(Long parentOptionId) {
		String sql = "SELECT * FROM CAR_OPTION WHERE parent_option_id = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), parentOptionId);
	}

	@Override
	public List<CarOption> findByCategory(String category) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), category);
	}

	@Override
	public List<CarOption> findByCategoryDetail(String categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category_detail = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), categoryDetail);
	}

	@Override
	public List<CarOption> findByCategoryDetailAndParentOptionIdIsNull(String categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category_detail = ? AND parent_option_id IS NULL;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), categoryDetail);
	}

	@Override
	public boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, String categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE car_option_id = ? AND category_detail = ?;";
		List<CarOption> results = jdbcTemplate.query(sql, carOptionRowMapper(), carOptionId, categoryDetail);
		return !results.isEmpty();
	}

	private RowMapper<CarOption> carOptionRowMapper() {
		return (rs, rowNum) -> {
			CarOption carOption = new CarOption(
				rs.getLong("car_option_id"),
				rs.getString("category"),
				rs.getString("category_detail"),
				rs.getString("option_name"),
				rs.getString("option_detail"),
				rs.getInt("price"),
				rs.getString("photo_url"),
				rs.getLong("parent_option_id")
			);
			return carOption;
		};
	}
}
