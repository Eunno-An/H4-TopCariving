package com.backend.topcariving.domain.option.dto.response.archiving;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "차량 아카이빙에 대한 응답 DTO")
public class ArchivingResponseDTO {

	@Schema(description = "최종 결과 조회하고자 하는 차량 아카이빙 ID", example = "1")
	private Long archivingId;

	@Schema(description = "선택한 차량 외장 사진", example = "https://www.test.com/car.png")
	private String photoUrl;

	@Schema(description = "선택한 모델")
	private ArchivingOptionResponseDTO model;

	@Schema(description = "선택한 엔진")
	private ArchivingOptionResponseDTO engine;

	@Schema(description = "선택한 바디타입")
	private ArchivingOptionResponseDTO bodyType;

	@Schema(description = "선택한 구동방식")
	private ArchivingOptionResponseDTO drivingMethod;

	@Schema(description = "선택한 외장색상")
	private ArchivingColorResponseDTO exteriorColor;

	@Schema(description = "선택한 내장색상")
	private ArchivingColorResponseDTO interiorColor;

	@Schema(description = "선택한 선택옵션")
	private List<ArchivingOptionResponseDTO> selectOptions;

}
