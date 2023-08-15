package com.backend.topcariving.domain.review.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TagReviewRepositoryImpl implements TagReviewRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<TagResponseDTO> findTagResponseDTOByCarOptionIdAndLimit(Long carOptionId, int limit) {
		String sql = "SELECT TAG.tag_text AS TAG_CONTENT, COUNT(*) "
			+ "FROM MY_CAR AS MC "
			+ "INNER JOIN TAG_REVIEW AS TR ON MC.my_car_id = TR.my_car_id "
			+ "INNER JOIN CAR_OPTION AS CO ON MC.car_option_id = CO.car_option_id "
			+ "INNER JOIN TAG ON TAG.tag_id = TR.tag_id "
			+ "WHERE CO.car_option_id = ? "
			+ "GROUP BY (TAG_TEXT, OPTION_NAME) "
			+ "ORDER BY (COUNT(*), TAG_TEXT) DESC "
			+ "LIMIT ?;";
		return jdbcTemplate.query(sql,
			(rs, rowNum) -> {
				return new TagResponseDTO(rs.getString("tag_content"));
			}, carOptionId, limit);
	}
}
