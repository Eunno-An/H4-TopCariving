package com.backend.topcariving.domain.entity.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarOptionTag {

	private Long carOptionTagId;

	// FK
	private Long tagId;

	private Long carOptionId;
}
