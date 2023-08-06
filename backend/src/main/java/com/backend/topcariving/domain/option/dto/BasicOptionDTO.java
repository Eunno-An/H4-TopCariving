package com.backend.topcariving.domain.option.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "기본 포함 품목에 대한 정보를 담고있는 객체")
public class BasicOptionDTO {

	@Schema(description = "카테고리를 위한 객체")
	private List<String> categories;

	@Schema(description = "해당 옵션에 대한 세부내역들을 저장하고 있는 객체")
	private List<TrimResponseDTO> data;
}
