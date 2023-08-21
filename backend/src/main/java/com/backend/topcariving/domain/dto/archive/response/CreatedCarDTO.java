package com.backend.topcariving.domain.dto.archive.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "마이카이빙 화면에서 내가 만든 차량 대한 DTO")
public class CreatedCarDTO {

	@Schema(description = "차량 아카이빙 ID", example = "1")
	private Long archivingId;

	@Schema(description = "trim에 대한 정보(모델, 엔진, 바디타입, 구동방식)")
	private Map<String, String> trims;

	@Schema(description = "차량을 만든 일시", example = "2023-08-01 12:00:00")
	private LocalDateTime dayTime;

	@Schema(description = "임시저장 여부(false: 임시저장)", example = "true")
	private boolean isComplete;

	@Schema(description = "선택 옵션에 대한 이름, 사진")
	private List<PictureSelectedOptionDTO> selectedOptions;
}
