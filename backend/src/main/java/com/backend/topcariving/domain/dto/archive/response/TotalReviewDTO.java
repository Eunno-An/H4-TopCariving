package com.backend.topcariving.domain.dto.archive.response;

import java.util.List;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TotalReviewDTO {

	@Schema(description = "차량 전체에 대한 텍스트 리뷰", example = "너무 좋요아")
	private String review;

	@Schema(description = "차량 전체에 대한 태그 리뷰")
	private List<TagResponseDTO> tags;
}
