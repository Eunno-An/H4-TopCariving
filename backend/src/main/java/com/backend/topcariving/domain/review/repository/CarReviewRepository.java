package com.backend.topcariving.domain.review.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.backend.topcariving.domain.archive.dto.TotalReviewDTO;
import com.backend.topcariving.domain.review.entity.CarReview;

public interface CarReviewRepository {

	Optional<CarReview> findByArchivingId(Long archivingId);

	Map<Long, TotalReviewDTO> findTotalReviewDTOsByArchivingIds(List<Long> archivingIds);
}
