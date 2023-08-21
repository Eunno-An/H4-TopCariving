package com.backend.topcariving.domain.dto.option.response.archiving;

import java.util.ArrayList;
import java.util.List;

import com.backend.topcariving.domain.entity.option.CarOption;

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

	// todo: service 단에서 수행하기
	public static List<ArchivingOptionResponseDTO> from(List<CarOption> carOptions) {
		List<ArchivingOptionResponseDTO> archivingOptionResponseDTOs = new ArrayList<>();
		carOptions.forEach(carOption -> {
			archivingOptionResponseDTOs.add(from(carOption));
		});
		return archivingOptionResponseDTOs;
	}
}
