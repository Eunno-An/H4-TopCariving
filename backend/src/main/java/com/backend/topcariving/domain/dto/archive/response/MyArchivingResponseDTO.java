package com.backend.topcariving.domain.dto.archive.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "마이카이빙 화면에 대한 응답 DTO")
public class MyArchivingResponseDTO {

	@Schema(description = "내가 만든 차량 목록")
	private List<CreatedCarDTO> createdCar;

	@Schema(description = "피드에서 저장한 차량 목록")
	private List<ArchiveFeedDTO> archiveSearches;
}
