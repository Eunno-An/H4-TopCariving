package com.backend.topcariving.domain.option.entity;

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
