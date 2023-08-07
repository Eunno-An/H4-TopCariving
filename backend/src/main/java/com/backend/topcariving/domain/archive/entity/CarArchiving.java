package com.backend.topcariving.domain.archive.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Table("CAR_ARCHIVING")
public class CarArchiving {

	@Id
	private Long archivingId;

	@CreatedDate
	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	// FK
	private Long userId;
}
