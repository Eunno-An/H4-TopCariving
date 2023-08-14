package com.backend.topcariving.domain.archive.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량에 대한 세부정보 응답 DTO")
public class ArchiveDetailResponseDTO {

	@Schema(description = "차량 아카이브 ID", example = "1")
	private Long archivingId;

	@Schema(description = "생성, 시승 및 구매 일시", example = "2023-08-01 12:00:00")
	private LocalDateTime dayTime;

	@Schema(description = "차량 아카이빙 타입", example = "시승/구매/내 차 만들기")
	private String archivingType;

	@Schema(description = "차량 옵션들")
	private Map<String, List<OptionSummaryDTO>> carArchiveResult;

	@Schema(description = "총 가격")
	private Integer totalPrice;

	@Schema(description = "포지션 리스트")
	private List<PositionDTO> positions;

	@Schema(description = "북마크 여부(true: 북마크함)", example = "true")
	private Boolean isBookmarked;

	@Schema(description = "선택 옵션 디테일")
	private List<OptionDetailDTO> optionDetails;

	@Schema(description = "차량 전체 리뷰", example = "너무 좋아요~~")
	private String review;
}
