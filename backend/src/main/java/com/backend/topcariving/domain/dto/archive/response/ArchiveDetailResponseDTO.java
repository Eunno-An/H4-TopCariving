package com.backend.topcariving.domain.dto.archive.response;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.topcariving.domain.dto.option.response.OptionDetailDTO;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.domain.entity.review.CarReview;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "차량에 대한 세부정보 응답 DTO")
public class ArchiveDetailResponseDTO {

	@Schema(description = "차량 아카이브 ID", example = "1")
	private Long archivingId;

	@Schema(description = "생성, 시승 및 구매 일시", example = "2023-08-01 12:00:00")
	private LocalDateTime dayTime;

	@Schema(description = "차량 아카이빙 타입", example = "시승/구매/내 차 만들기")
	private ArchivingType archivingType;

	@Schema(description = "총 가격")
	private Integer totalPrice;

	@Schema(description = "차량 이미지")
	private String photoUrl;

	@Schema(description = "포지션 리스트")
	private List<PositionDTO> positions;

	@Schema(description = "북마크 여부(true: 북마크함)", example = "true")
	private Boolean isBookmarked;

	@Schema(description = "차량 옵션 디테일")
	private List<OptionDetailDTO> optionDetails;

	@Schema(description = "차량 전체 텍스트 리뷰", example = "너무 좋아요~~")
	private String carReview;

	@Schema(description = "차량 전체 태그 리뷰")
	private List<TagResponseDTO> tags;

	public static ArchiveDetailResponseDTO of(CarArchiving carArchiving, int totalPrice, String photoUrl, List<PositionDTO> positions,
		boolean isBookmarked, List<OptionDetailDTO> optionDetails, CarReview carReview, List<TagResponseDTO> tags) {
		return ArchiveDetailResponseDTO.builder()
			.archivingId(carArchiving.getArchivingId())
			.dayTime(carArchiving.getDayTime())
			.archivingType(carArchiving.getArchivingType())
			.totalPrice(totalPrice)
			.photoUrl(photoUrl)
			.positions(positions)
			.isBookmarked(isBookmarked)
			.optionDetails(optionDetails)
			.carReview(carReview == null ? null : carReview.getReview())
			.tags(tags)
			.build();
	}
}
