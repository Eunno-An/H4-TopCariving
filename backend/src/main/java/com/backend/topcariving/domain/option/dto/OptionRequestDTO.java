package com.backend.topcariving.domain.option.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "선택한 옵션이 여러개일 때 받는 RequestDTO")
public class OptionRequestDTO {

	@Schema(description = "선택한 옵션의 ID들")
	public List<Long> ids;
}
