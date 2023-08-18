package com.backend.topcariving.domain.archive.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MyCar {

	private Long myCarId;

	// FK
	private Long carOptionId;

	private Long archivingId;

}