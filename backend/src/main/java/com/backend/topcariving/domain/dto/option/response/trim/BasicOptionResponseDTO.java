package com.backend.topcariving.domain.dto.option.response.trim;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "기본 포함 품목에 대한 정보를 담고있는 객체")
public class BasicOptionResponseDTO {

	@Schema(description = "해당 옵션에 대한 세부내역들을 저장하고 있는 객체")
	private Map<String, List<OptionResponseDTO>> data;

	public static BasicOptionResponseDTO of(Map<String, List<OptionResponseDTO>> data) {
		return BasicOptionResponseDTO.builder()
			.data(data)
			.build();
	}
}
