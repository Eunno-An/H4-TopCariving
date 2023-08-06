package com.backend.topcariving.domain.option.dto;

import java.util.List;

import com.backend.topcariving.domain.tag.dto.TagResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "선택, 기본, H Genuine, N Performance에서 저장하는 DTO")
public class SelectionResponseDTO {

	@Schema(description = "옵션 ID", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "컴포트2")
	private String optionName;

	@Schema(description = "가격", example = "6900000")
	private int price;

	@Schema(description = "사진 URL", example = "https://topcariving.com/photo.jpg")
	private String photoUrl;

	@Schema(description = "세부 옵션 및 옵션에 대한 설명들")
	private List<SelectionDetailDTO> details;

	@Schema(description = "옵션에 대한 태그들")
	private List<TagResponseDTO> tags;
}
