package com.backend.topcariving.domain.option.dto.request.color;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "외장, 내장의 색상을 한번에 선택할 때 받는 Request DTO")
public class BothColorRequestDTO {

	private Long userId;

	private Long exteriorColorOptionId;

	private Long interiorColorOptionId;

	private Long archivingId;

	@Builder
	public BothColorRequestDTO(final Long userId, final Long exteriorColorOptionId, final Long interiorColorOptionId,
		final Long archivingId) {
		this.userId = userId;
		this.exteriorColorOptionId = exteriorColorOptionId;
		this.interiorColorOptionId = interiorColorOptionId;
		this.archivingId = archivingId;
	}
}
