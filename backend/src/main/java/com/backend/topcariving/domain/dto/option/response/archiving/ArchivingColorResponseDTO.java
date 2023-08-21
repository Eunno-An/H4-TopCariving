package com.backend.topcariving.domain.dto.option.response.archiving;

import com.backend.topcariving.domain.entity.option.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "차량 아카이빙의 색상에 대한 응답 DTO")
public class ArchivingColorResponseDTO {

	@Schema(description = "옵션 ID", example = "11")
	private Long carOptionId;

	@Schema(description = "옵션명", example = "어비스블랙펄")
	private String optionName;

	@Schema(description = "옵션 가격", example = "0")
	private int price;

	@Schema(description = "옵션의 사진 파일 경로", example = "https://www.test.com/black.png")
	private String photoUrl;

	public static ArchivingColorResponseDTO from(CarOption carOption) {
		return ArchivingColorResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.price(carOption.getPrice())
			.photoUrl(carOption.getPhotoUrl())
			.build();
	}
}
