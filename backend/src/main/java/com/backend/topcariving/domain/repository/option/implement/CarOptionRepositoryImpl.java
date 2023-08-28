package com.backend.topcariving.domain.repository.option.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;

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
	public List<CarOption> findByCategory(Category category) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), category.getName());
	}

	@Override
	public List<CarOption> findByCategoryAndParentOptionIdIsNull(Category category) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category = ? AND parent_option_id IS NULL ORDER BY car_option_id ASC;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), category.getName());
	}

	@Override
	public List<CarOption> findByCategoryDetail(CategoryDetail categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category_detail = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), categoryDetail.getName());
	}

	@Override
	public List<CarOption> findByCategoryDetailAndParentOptionIdIsNull(CategoryDetail categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE category_detail = ? AND parent_option_id IS NULL;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), categoryDetail.getName());
	}

	@Override
	public boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, CategoryDetail categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION WHERE car_option_id = ? AND category_detail = ?;";
		List<CarOption> results = jdbcTemplate.query(sql, carOptionRowMapper(), carOptionId, categoryDetail.getName());
		return !results.isEmpty();
	}

	@Override
	public List<CarOption> findByIds(final List<Long> ids) {
		String sql = "SELECT * FROM CAR_OPTION WHERE car_option_id IN (:ids)";

		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ids", ids);

		return namedParameterJdbcTemplate.query(sql, paramMap, carOptionRowMapper());
	}

	@Override
	public List<CarOption> findByArchivingId(Long archivingId) {
		String sql = "SELECT C.* "
			+ "FROM CAR_OPTION C "
			+ "JOIN MY_CAR M ON C.car_option_id = M.car_option_id "
			+ "WHERE M.archiving_id = ?;";
		return jdbcTemplate.query(sql, carOptionRowMapper(), archivingId);
	}

	@Override
	public List<String> findStringByParentOptionId(Long carOptionId) {
		String sql = "SELECT option_name FROM CAR_OPTION WHERE parent_option_id = ?;";

		return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("option_name"), carOptionId);
	}

	@Override
	public Optional<CarOption> findByArchivingIdAndCategoryDetail(Long archivingId, CategoryDetail categoryDetail) {
		String sql = "SELECT * FROM CAR_OPTION CO "
			+ "INNER JOIN MY_CAR MC ON CO.car_option_id = MC.car_option_id "
			+ "INNER JOIN CAR_ARCHIVING CA on MC.archiving_id = CA.archiving_id "
			+ "WHERE MC.archiving_id = ? AND category_detail = ?;";

		List<CarOption> results = jdbcTemplate.query(sql, carOptionRowMapper(), archivingId, categoryDetail.getName());

		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	private RowMapper<CarOption> carOptionRowMapper() {
		return (rs, rowNum) -> new CarOption(
			rs.getLong("car_option_id"),
			Category.valueOfName(rs.getString("category")),
			CategoryDetail.valueOfName(rs.getString("category_detail")),
			rs.getString("option_name"),
			rs.getString("option_detail"),
			rs.getInt("price"),
			rs.getString("photo_url"),
			rs.getLong("parent_option_id")
		);
	}
}
