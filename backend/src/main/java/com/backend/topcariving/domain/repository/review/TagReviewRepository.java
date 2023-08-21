package com.backend.topcariving.domain.repository.review;

import java.util.List;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;

public interface TagReviewRepository {

	List<TagResponseDTO> findTagResponseDTOByCarOptionIdAndLimit(Long carOptionId, int limit);

	List<TagResponseDTO> findTagResponseDTOByArchivingId(Long archivingId);

	List<TagResponseDTO> findTagResponseDTOByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);
}
