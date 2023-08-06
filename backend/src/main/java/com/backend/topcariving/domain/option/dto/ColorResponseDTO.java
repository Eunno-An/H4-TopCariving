package com.backend.topcariving.domain.option.dto;

import java.util.List;

import com.backend.topcariving.domain.tag.dto.TagResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "색상에 대한 응답 DTO")
public class ColorResponseDTO {

	@Schema(description = "옵션의 id 값", example = "11")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "어비스블랙펄")
	private String optionName;

	@Schema(description = "옵션의 사진 파일 경로", example = "https://www.test.com/black.png")
	private String photoUrl;

	@Schema(description = "옵션의 가격", example = "10000000")
	private int price;

	@Schema(description = "다른 사용자들이 남긴 태그 후기")
	private List<TagResponseDTO> tagResponses;

}
