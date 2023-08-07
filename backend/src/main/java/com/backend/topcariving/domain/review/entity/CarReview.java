package com.backend.topcariving.domain.review.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Table("CAR_REVIEW")
public class CarReview {

	@Id
	private Long carReviewId;

	private String review;

	// FK
	private Long myCarId;
}
