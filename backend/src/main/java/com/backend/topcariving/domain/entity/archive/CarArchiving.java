package com.backend.topcariving.domain.entity.archive;

import java.time.LocalDateTime;

import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarArchiving {

	private Long archivingId;

	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	private ArchivingType archivingType;

	// FK
	private Long userId;
}
