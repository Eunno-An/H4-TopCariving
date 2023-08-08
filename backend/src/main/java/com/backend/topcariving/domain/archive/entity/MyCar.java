package com.backend.topcariving.domain.archive.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Table("MY_CAR")
public class MyCar {

	@Id
	private Long myCarId;

	// FK
	private Long carOptionId;

	private Long archivingId;
}
