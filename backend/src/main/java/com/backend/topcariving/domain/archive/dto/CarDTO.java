package com.backend.topcariving.domain.archive.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.topcariving.domain.option.entity.CarOption;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarDTO {

	private Long archivingId;

	private LocalDateTime dayTime;

	private Boolean isComplete;

	private Boolean isAlive;

	private String archivingType;

	private List<CarOption> carOptions;
}
