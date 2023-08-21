package com.backend.topcariving.domain.repository.review.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.backend.topcariving.domain.dto.archive.response.TotalReviewDTO;
import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;

public class TotalReviewRowMapper implements RowMapper<Map<Long, TotalReviewDTO>> {
	@Override
	public Map<Long, TotalReviewDTO> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<Long, TotalReviewDTO> result = new HashMap<>();

		do {
			long archivingId = rs.getLong("archiving_id");
			if (!result.containsKey(archivingId)) {
				TotalReviewDTO review = new TotalReviewDTO(rs.getString("review"), new ArrayList<>());
				result.put(archivingId, review);
			}
			TotalReviewDTO totalReviewDTO = result.get(archivingId);
			List<TagResponseDTO> tags = totalReviewDTO.getTags();
			tags.add(new TagResponseDTO(rs.getString("tag_text")));
		} while (rs.next());

		return result;
	}
}
