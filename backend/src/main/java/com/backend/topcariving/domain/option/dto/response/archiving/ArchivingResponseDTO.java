package com.backend.topcariving.domain.option.dto.response.archiving;

import java.util.List;
import java.util.Map;

import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량 아카이빙에 대한 응답 DTO")
public class ArchivingResponseDTO {

	@Schema(description = "최종 결과 조회하고자 하는 차량 아카이빙 ID", example = "1")
	private Long archivingId;

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

	public static ArchivingResponseDTO of(Long archivingId, Map<CategoryDetail, ArchivingOptionResponseDTO> archivingOptionResponseDTOs,
		Map<CategoryDetail, ArchivingColorResponseDTO> archivingColorResponseDTOs, List<ArchivingOptionResponseDTO> selectOptions) {
		return ArchivingResponseDTO.builder()
			.archivingId(archivingId)
			.model(archivingOptionResponseDTOs.get(CategoryDetail.MODEL))
			.engine(archivingOptionResponseDTOs.get(CategoryDetail.ENGINE))
			.bodyType(archivingOptionResponseDTOs.get(CategoryDetail.BODY_TYPE))
			.drivingMethod(archivingOptionResponseDTOs.get(CategoryDetail.DRIVING_METHOD))
			.exteriorColor(archivingColorResponseDTOs.get(CategoryDetail.EXTERIOR_COLOR))
			.interiorColor(archivingColorResponseDTOs.get(CategoryDetail.INTERIOR_COLOR))
			.selectOptions(selectOptions)
			.build();
	}

}
