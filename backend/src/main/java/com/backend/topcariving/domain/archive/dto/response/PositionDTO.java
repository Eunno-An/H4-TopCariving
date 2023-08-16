package com.backend.topcariving.domain.archive.dto.response;

import com.backend.topcariving.domain.option.entity.Position;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량에서 옵션의 위치를 출력하기 위한 DTO")
public class PositionDTO {

	@Schema(description = "위치값 ID", example = "1")
	private Long positionId;

	@Schema(description = "왼쪽에서의 퍼센트값", example = "65%")
	private String leftPercent;

	@Schema(description = "위쪽에서의 퍼센트값", example = "35%")
	private String topPercent;

	public static PositionDTO from(Position position) {
		return PositionDTO.builder()
			.positionId(position.getPositionId())
			.leftPercent(position.getLeftPercent())
			.topPercent(position.getTopPercent())
			.build();
	}
}
