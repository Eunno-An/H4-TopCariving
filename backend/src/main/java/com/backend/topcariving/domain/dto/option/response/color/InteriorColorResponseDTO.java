package com.backend.topcariving.domain.dto.option.response.color;

import java.util.List;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "내장 색상에 대한 응답 DTO")
public class InteriorColorResponseDTO {

	@Schema(description = "옵션의 id 값", example = "17")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "퀄팅천연(블랙)")
	private String optionName;

	@Schema(description = "옵션의 내장 색상 사진 파일 경로", example = "https://www.test.com/black_internal.png")
	private String photoUrl;

	@Schema(description = "옵션의 색상 사진 파일 경로", example = "https://www.test.com/black.png")
	private String colorUrl;

	@Schema(description = "옵션의 가격", example = "0")
	private int price;

	@Schema(description = "다른 사용자들이 남긴 태그 후기")
	private List<TagResponseDTO> tagResponses;

	public static InteriorColorResponseDTO of(CarOption carOption, String photoUrl, List<TagResponseDTO> tagResponses) {
		return InteriorColorResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.photoUrl(photoUrl)
			.colorUrl(carOption.getPhotoUrl())
			.price(carOption.getPrice())
			.tagResponses(tagResponses)
			.build();
	}

}
