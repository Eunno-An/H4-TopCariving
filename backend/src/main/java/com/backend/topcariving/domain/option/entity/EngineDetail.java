package com.backend.topcariving.domain.option.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EngineDetail {

	private Long engineDetailId;

	private String maxOutput;

	private String maxTorque;

	// FK
	private Long carOptionId;
}
