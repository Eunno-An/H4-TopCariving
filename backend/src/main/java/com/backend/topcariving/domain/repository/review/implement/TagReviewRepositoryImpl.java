package com.backend.topcariving.domain.repository.review.implement;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.repository.review.TagReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TagReviewRepositoryImpl implements TagReviewRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	@Cacheable(value = "tagLimit", key = "#carOptionId")
	public List<TagResponseDTO> findTagResponseDTOByCarOptionIdAndLimit(Long carOptionId, int limit) {
		String sql = "SELECT TAG.tag_text AS TAG_CONTENT, COUNT(*) "
			+ "FROM MY_CAR AS MC "
			+ "INNER JOIN TAG_REVIEW AS TR ON MC.my_car_id = TR.my_car_id "
			+ "INNER JOIN CAR_OPTION AS CO ON MC.car_option_id = CO.car_option_id "
			+ "INNER JOIN TAG ON TAG.tag_id = TR.tag_id "
			+ "WHERE CO.car_option_id = ? "
			+ "GROUP BY TAG_TEXT, OPTION_NAME "
			+ "ORDER BY COUNT(*) DESC, TAG_TEXT "
			+ "LIMIT ?;";
		return jdbcTemplate.query(sql,
			(rs, rowNum) -> {
				return new TagResponseDTO(rs.getString("tag_content"));
			}, carOptionId, limit);
	}

	@Override
	@Cacheable(value = "tagArchiving", key = "#archivingId")
	public List<TagResponseDTO> findTagResponseDTOByArchivingId(Long archivingId) {
		String sql = "SELECT TAG.tag_text AS TAG_CONTENT "
			+ "FROM TAG_REVIEW TR "
			+ "INNER JOIN TAG ON TAG.tag_id = TR.tag_id "
			+ "INNER JOIN MY_CAR MC ON TR.my_car_id = MC.my_car_id "
			+ "WHERE MC.archiving_id = ? AND MC.car_option_id IS NULL;";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new TagResponseDTO(rs.getString("tag_content")), archivingId);
	}

	@Override
	@Cacheable(value = "tagArchivingCarOption")
	public List<TagResponseDTO> findTagResponseDTOByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId) {
		String sql = "SELECT TAG.tag_text AS TAG_CONTENT "
			+ "FROM TAG_REVIEW TR "
			+ "INNER JOIN TAG ON TAG.tag_id = TR.tag_id "
			+ "INNER JOIN MY_CAR MC ON TR.my_car_id = MC.my_car_id "
			+ "WHERE MC.archiving_id = ? AND MC.car_option_id = ?;";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new TagResponseDTO(rs.getString("tag_content")), archivingId, carOptionId);
	}
}
