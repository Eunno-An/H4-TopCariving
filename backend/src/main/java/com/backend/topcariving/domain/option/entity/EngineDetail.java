package com.backend.topcariving.domain.option.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Table("ENGINE_DETAIL")
@AllArgsConstructor
@Getter
public class EngineDetail {

	@Id
	private Long engineDetailId;

	private String maxOutput;

	private String maxTorque;

	// FK
	private Long carOptionId;
}
