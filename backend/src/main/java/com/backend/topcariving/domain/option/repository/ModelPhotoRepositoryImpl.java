package com.backend.topcariving.domain.option.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.ModelPhoto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ModelPhotoRepositoryImpl implements ModelPhotoRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<ModelPhoto> findAllByCarOptionId(Long carOptionId) {
		String sql = "SELECT * FROM MODEL_PHOTO WHERE car_option_id = ?;";

		return jdbcTemplate.query(sql,
			(rs, rowNum) -> {
				return new ModelPhoto(
					rs.getLong("model_photo_id"),
					rs.getString("content"),
					rs.getString("photo_svg_url"),
					rs.getString("photo_png_url"),
					rs.getLong("car_option_id")
				);
			}, carOptionId);
	}
}
