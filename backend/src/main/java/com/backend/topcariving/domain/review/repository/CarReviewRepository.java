package com.backend.topcariving.domain.review.repository;

import java.util.Optional;

import com.backend.topcariving.domain.review.entity.CarReview;

public interface CarReviewRepository {

	Optional<CarReview> findByMyCarId(Long myCarId);
}
