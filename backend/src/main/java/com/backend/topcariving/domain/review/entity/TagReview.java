package com.backend.topcariving.domain.review.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Table("TAG_REVIEW")
public class TagReview {

	@Id
	private Long tagReviewId;

	private Long tagId;

	private Long myCarId;
}
