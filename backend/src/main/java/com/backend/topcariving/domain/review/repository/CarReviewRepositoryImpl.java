package com.backend.topcariving.domain.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.review.entity.CarReview;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CarReviewRepositoryImpl implements CarReviewRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<CarReview> findByArchivingId(Long archivingId) {
		String sql = "SELECT * FROM CAR_REVIEW "
			+ "WHERE my_car_id IN "
			+ "(SELECT my_car_id FROM MY_CAR WHERE archiving_id = ? AND car_option_id IS NULL);";
		List<CarReview> results = jdbcTemplate.query(sql, carReviewRowMapper(), archivingId);
		if (results.isEmpty())
			return Optional.empty();
		return Optional.ofNullable(results.get(0));
	}

	private RowMapper<CarReview> carReviewRowMapper() {
		return (rs, rowNum) -> new CarReview(
			rs.getLong("car_review_id"),
			rs.getString("review"),
			rs.getLong("my_car_id")
		);
	}
}
