package com.backend.topcariving.domain.repository.review;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.backend.topcariving.domain.dto.archive.response.TotalReviewDTO;
import com.backend.topcariving.domain.entity.review.CarReview;

public interface CarReviewRepository {

	Optional<CarReview> findByArchivingId(Long archivingId);

	Map<Long, TotalReviewDTO> findTotalReviewDTOsByArchivingIds(List<Long> archivingIds);
}
