package com.backend.topcariving.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarReview {

	private Long carReviewId;

	private String review;

	// FK
	private Long myCarId;
}
