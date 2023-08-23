package com.backend.topcariving.domain.dto.option.response.selection;

import com.backend.topcariving.domain.entity.option.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "옵션의 하위항목에 대한 정보")
public class SelectionDetailDTO {

	@Schema(description = "항목에 대한 ID 값", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "후방 주차 충돌방지 보조")
	private String optionName;

	@Schema(description = "옵션 설명", example = "주요 주행 정보를 전면 윈드실드에 표시합니다.")
	private String optionDetail;

	@Schema(description = "옵션 사진", example = "https://www.test.com/picture.png")
	private String photoUrl;

	public static SelectionDetailDTO from(CarOption carOption) {
		return SelectionDetailDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName((carOption.getOptionName()))
			.optionDetail(carOption.getOptionDetail())
			.photoUrl(carOption.getPhotoUrl())
			.build();
	}
}
