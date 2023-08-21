package com.backend.topcariving.domain.dto.option.response.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "태그에 대한 응답 DTO")
public class TagResponseDTO {

	@Schema(description = "태그 내용")
	private String tagContent;
}
