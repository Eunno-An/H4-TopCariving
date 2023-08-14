package com.backend.topcariving.domain.archive.dto.response;

import java.util.List;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

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

	@Schema(description = "옵션 하위 품목 (하위 품목이 없으면 null)")
	private List<String> childOptions;

	@Schema(description = "포지션 ID(포지션이 없는 옵션은 null)", example = "1")
	private Long positionId;

	@Schema(description = "옵션에 대한 태그 리뷰들")
	private List<TagResponseDTO> tags;
}
