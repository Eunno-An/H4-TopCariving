package com.backend.topcariving.domain.dto.option.response.archiving;

import java.util.List;

import com.backend.topcariving.domain.entity.option.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량 아카이빙의 선택 옵션에 대한 응답 DTO")
public class ArchivingOptionDetailResponseDTO {

	@Schema(description = "옵션 ID")
	private Long carOptionId;

	@Schema(description = "옵션명")
	private String optionName;

	@Schema(description = "옵션 가격")
	private int price;

	@Schema(description = "옵션 설명")
	private List<ArchivingOptionResponseDTO> optionDetail;

	public static ArchivingOptionDetailResponseDTO of(CarOption carOption, List<ArchivingOptionResponseDTO> optionDetail) {
		return ArchivingOptionDetailResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.price(carOption.getPrice())
			.optionDetail(optionDetail)
			.build();
	}
}
