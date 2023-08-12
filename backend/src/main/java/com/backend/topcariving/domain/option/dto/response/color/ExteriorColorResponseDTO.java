package com.backend.topcariving.domain.option.dto.response.color;

import java.util.List;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "외장 색상에 대한 응답 DTO")
public class ExteriorColorResponseDTO {

	@Schema(description = "옵션의 id 값", example = "11")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "어비스블랙펄")
	private String optionName;

	@Schema(description = "옵션의 사진 파일 경로", example = "https://www.test.com/black.png")
	private String colorUrl;

	@Schema(description = "옵션의 가격", example = "10000000")
	private int price;

	@Schema(description = "다른 사용자들이 남긴 태그 후기")
	private List<TagResponseDTO> tagResponses;

	public static ExteriorColorResponseDTO of(CarOption carOption, List<TagResponseDTO> tagResponses) {
		return ExteriorColorResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.colorUrl(carOption.getPhotoUrl())
			.price(carOption.getPrice())
			.tagResponses(tagResponses)
			.build();
	}
}
