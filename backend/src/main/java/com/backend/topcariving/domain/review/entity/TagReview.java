package com.backend.topcariving.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagReview {

	private Long tagReviewId;

	private Long tagId;

	private Long myCarId;
}
