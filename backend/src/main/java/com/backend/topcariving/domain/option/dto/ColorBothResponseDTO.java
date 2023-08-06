package com.backend.topcariving.domain.option.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "외장, 내장 색상에 대한 응답 DTO")
public class ColorBothResponseDTO {

	@Schema(description = "외장 색상 응답 DTO")
	private List<ColorResponseDTO> exteriorColorResponses;

	@Schema(description = "내장 색상 응답 DTO")
	private List<ColorResponseDTO> interiorColorResponses;
}
