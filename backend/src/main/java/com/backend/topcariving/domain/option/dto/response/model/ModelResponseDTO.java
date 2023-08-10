package com.backend.topcariving.domain.option.dto.response.model;

import java.util.List;

import com.backend.topcariving.domain.option.entity.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Trim Response : 트림 정보를 반환하는 dto")
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ModelResponseDTO {

	@Schema(description = "옵션의 ID값", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "르블랑")
	private String optionName;

	@Schema(description = "옵션의 가격", example = "1476000")
	private int price;

	@Schema(description = "옵션의 사진 url들")
	private List<ModelPhotoDTO> photos;

	public static ModelResponseDTO of(CarOption option, List<ModelPhotoDTO> photos) {
		return ModelResponseDTO.builder()
			.carOptionId(option.getCarOptionId())
			.optionName(option.getOptionName())
			.price(option.getPrice())
			.photos(photos)
			.build();
	}
}
