package com.backend.topcariving.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.review.entity.CarReview;
import com.backend.topcariving.domain.review.repository.CarReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarReviewService {

	private final CarReviewRepository carReviewRepository;

	public CarReview getCarReview(MyCar myCar) {
		return carReviewRepository.findByMyCarId(myCar.getMyCarId())
			.orElse(null);
	}
}
