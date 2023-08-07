package com.backend.topcariving.domain.option.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Table("CAR_OPTION_TAG")
@AllArgsConstructor
@Getter
public class CarOptionTag {

	@Id
	private Long carOptionTagId;

	// FK
	private Long tagId;

	private Long carOptionId;
}
