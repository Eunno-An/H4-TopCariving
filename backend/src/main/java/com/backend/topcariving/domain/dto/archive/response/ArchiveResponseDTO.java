package com.backend.topcariving.domain.dto.archive.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "아카이빙 검색 화면에 대한 응답 DTO")
public class ArchiveResponseDTO {

	@Schema(description = "선택할 수 있는 모든 옵션들")
	private List<SearchOptionDTO> options;

	@Schema(description = "피드에서 조회한 결과")
	private List<ArchiveFeedDTO> archiveSearchResponses;

	public static ArchiveResponseDTO of(List<SearchOptionDTO> options, List<ArchiveFeedDTO> archiveSearchResponses) {
		return ArchiveResponseDTO.builder()
			.options(options)
			.archiveSearchResponses(archiveSearchResponses)
			.build();
	}
}
