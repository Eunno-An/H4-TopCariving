package com.backend.topcariving.domain.dto.option.response.color;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "외장, 내장 색상에 대한 응답 DTO")
public class BothColorResponseDTO {

	@Schema(description = "외장 색상 응답 DTO")
	private List<ExteriorColorResponseDTO> exteriorColorResponses;

	@Schema(description = "내장 색상 응답 DTO")
	private List<InteriorColorResponseDTO> interiorColorResponses;
}
