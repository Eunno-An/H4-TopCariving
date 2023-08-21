package com.backend.topcariving.domain.entity.archive;

import java.time.LocalDateTime;

import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.global.utils.serializer.ArchivingTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarArchiving {

	private Long archivingId;

	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	@JsonSerialize(using = ArchivingTypeSerializer.class)
	private ArchivingType archivingType;

	// FK
	private Long userId;
}
