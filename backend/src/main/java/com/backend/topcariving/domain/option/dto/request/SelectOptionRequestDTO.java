package com.backend.topcariving.domain.option.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "선택한 옵션이 한 개인 경우 사용하는 Request DTO")
public class SelectOptionRequestDTO {

	@Schema(description = "차량 옵션 ID", example = "1")
	private Long carOptionId;

	@Schema(description = "아카이빙 ID (Model을 저장 시에는 null로 주시면 됩니다)", example = "1")
	private Long archivingId;

	@Builder
	public SelectOptionRequestDTO(Long carOptionId, Long archivingId) {
		this.carOptionId = carOptionId;
		this.archivingId = archivingId;
	}
}
