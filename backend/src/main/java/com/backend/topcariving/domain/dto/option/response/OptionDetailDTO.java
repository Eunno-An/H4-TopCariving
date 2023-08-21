package com.backend.topcariving.domain.dto.option.response;

import java.util.List;

import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "아카이빙에서 차량 조회 시 옵션에 대한 상세정보 DTO")
public class OptionDetailDTO {

	@Schema(description = "옵션 ID", example = "1")
	private Long carOptionId;

	@Schema(description = "옵션명", example = "컴포트 II")
	private String optionName;

	@Schema(description = "카테고리 세부내역 ex) 모델, 엔진 등등")
	private CategoryDetail categoryDetail;

	@Schema(description = "옵션의 사진 / 색상만 해당 내용이 들어감")
	private String photoUrl;

	@Schema(description = "옵션 하위 품목 (하위 품목이 없으면 null)")
	private List<String> childOptionNames;

	@Schema(description = "포지션 ID (포지션이 없는 옵션은 null)", example = "1")
	private Long positionId;

	@Schema(description = "옵션에 대한 태그 리뷰들")
	private List<TagResponseDTO> tags;

	public static OptionDetailDTO of(CarOption carOption, List<String> childOptionNames, Long positionId, List<TagResponseDTO> tags) {
		return OptionDetailDTO.builder()
			.carOptionId(carOption.getCarOptionId())
			.optionName(carOption.getOptionName())
			.categoryDetail(carOption.getCategoryDetail())
			.photoUrl(carOption.getPhotoUrl())
			.childOptionNames(childOptionNames)
			.positionId(positionId)
			.tags(tags)
			.build();
	}
}
