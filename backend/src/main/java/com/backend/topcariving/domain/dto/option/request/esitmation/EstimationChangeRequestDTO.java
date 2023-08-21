package com.backend.topcariving.domain.dto.option.request.esitmation;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "견적 요약 보기로 들어와서 변경한 옵션들의 ID 값을 저장하는 DTO")
public class EstimationChangeRequestDTO {

	@Schema(description = "변경을 하고 있는 차량 아카이빙 ID")
	private Long archivingId;

	@Schema(description = "선택한 옵션들의 ID들")
	private List<Long> optionIds;
}
