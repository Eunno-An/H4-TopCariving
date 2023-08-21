package com.backend.topcariving.domain.dto.projection;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.domain.entity.option.CarOption;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarDTO {

	private Long archivingId;

	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	private ArchivingType archivingType;

	private List<CarOption> carOptions;
}
