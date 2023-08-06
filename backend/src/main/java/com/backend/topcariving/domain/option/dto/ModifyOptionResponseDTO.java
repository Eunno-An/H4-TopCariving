package com.backend.topcariving.domain.option.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "옵션 변경에 대한 응답 DTO")
public class ModifyOptionResponseDTO {

	@Schema(description = "변경하고자 하는 차량의 전체 결과")
	private ArchivingResponseDTO archivingResult;

	@Schema(description = "변경하고자 하는 옵션의 ID")
	private Long carOptionId;
}
