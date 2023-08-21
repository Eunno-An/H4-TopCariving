package com.backend.topcariving.domain.dto.option.response.trim;

import com.backend.topcariving.domain.entity.option.CarOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Trim Response : 트림 정보를 반환하는 dto (바디타입, 구동방식등에 공용으로 사용중")
@Getter
@Builder
public class OptionResponseDTO {

	@Schema(description = "옵션의 id 값", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "디젤 2.2")
	private String optionName;

	@Schema(description = "옵션의 설명" , example = "높은 토크로 파워풀한 드라이빙이 가능합니다")
	private String optionDetail;

	@Schema(description = "옵션의 가격", example = "1480000")
	private int price;

	@Schema(description = "사진 url", example = "topcariving.com/engine.png")
	private String photoUrl;

	public static OptionResponseDTO from(CarOption carOption) {
		return OptionResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.optionDetail(carOption.getOptionDetail())
			.price(carOption.getPrice())
			.photoUrl(carOption.getPhotoUrl())
			.build();
	}
}
