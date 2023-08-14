package com.backend.topcariving.domain.review.repository;

import java.util.List;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

public interface TagReviewRepository {

	List<TagResponseDTO> findTagResponseDTOByCarOptionId(Long carOptionId);

	List<TagResponseDTO> findTagResponseDTOByCarOptionIdAndLimit(Long carOptionId, int limit);
}
