package com.backend.topcariving.domain.option.dto.response.engine;

import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.EngineDetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Engine Response : 엔진 정보를 반환하는 dto")
@Getter
@Builder
public class EngineResponseDTO {

	@Schema(description = "옵션의 id 값", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션 이름", example = "디젤 2.2")
	private String optionName;

	@Schema(description = "옵션의 설명" , example = "높은 토크로 파워풀한 드라이빙이 가능합니다")
	private String optionDetail;

	@Schema(description = "옵션의 가격", example = "1480000")
	private int price;

	@Schema(description = "최고 출력", example = "최고출력 295/6,000PS/rpm")
	private String maxOutput;

	@Schema(description = "최대 토크", example = "최대토크 36.2/5,200kgf-m/rpm")
	private String maxTorque;

	@Schema(description = "사진 url", example = "topcariving.com/engine.png")
	private String photoUrl;

	public static EngineResponseDTO of(CarOption carOption, EngineDetail engineDetail) {
		return EngineResponseDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.optionDetail(carOption.getOptionDetail())
			.price(carOption.getPrice())
			.maxOutput(engineDetail.getMaxOutput())
			.maxTorque(engineDetail.getMaxTorque())
			.photoUrl(carOption.getPhotoUrl())
			.build();
	}
}
