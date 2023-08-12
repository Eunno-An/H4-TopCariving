package com.backend.topcariving.domain.archive.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarArchiving {

	private Long archivingId;

	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	// FK
	private Long userId;
}
