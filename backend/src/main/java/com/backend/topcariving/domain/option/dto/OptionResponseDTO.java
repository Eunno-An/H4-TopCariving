package com.backend.topcariving.domain.option.dto;

import com.backend.topcariving.domain.option.entity.Option;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Trim Response : 트림 정보를 반환하는 dto")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OptionResponseDTO {

	@Schema(description = "옵션의 ID값", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션의 카테고리 이름", example = "트림")
	private String category;

	@Schema(description = "옵션의 카테고리 설명", example = "모델")
	private String categoryDetail;

	@Schema(description = "옵션 이름", example = "르블랑")
	private String optionName;

	@Schema(description = "옵션의 가격", example = "1476000")
	private int price;

	@Schema(description = "옵션의 사진 url", example = "https://www.test.png")
	private String photoUrl;

	public static OptionResponseDTO from(Option option) {
		return OptionResponseDTO.builder()
			.carOptionId(option.getCarOptionId())
			.category(option.getCategory())
			.categoryDetail(option.getCategoryDetail())
			.optionName(option.getOptionName())
			.price(option.getPrice())
			.photoUrl(option.getPhotoUrl())
			.build();
	}
}
