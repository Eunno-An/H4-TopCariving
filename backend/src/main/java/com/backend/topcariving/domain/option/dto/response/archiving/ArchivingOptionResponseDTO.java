package com.backend.topcariving.domain.option.dto.response.archiving;

import com.backend.topcariving.domain.option.entity.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량 아카이빙의 옵션에 대한 응답 DTO")
public class ArchivingOptionResponseDTO {

	@Schema(description = "옵션 ID")
	private Long carOptionId;

	@Schema(description = "옵션명")
	private String optionName;

	@Schema(description = "옵션 가격")
	private int price;

	public static ArchivingOptionResponseDTO from(CarOption carOption) {
		return ArchivingOptionResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.price(carOption.getPrice())
			.build();
	}
}
