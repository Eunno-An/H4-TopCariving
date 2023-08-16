package com.backend.topcariving.domain.archive.dto.response;

import com.backend.topcariving.domain.option.entity.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "아카이빙 검색 화면에 필요한 옵션 정보 DTO")
public class SearchOptionDTO {

	@Schema(description = "차량 옵션 ID", example = "103")
	private Long carOptionId;

	@Schema(description = "차량 옵션 이름", example = "컴포트 II")
	private String optionName;

	public static SearchOptionDTO from(CarOption carOption) {
		return SearchOptionDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.build();
	}
}
