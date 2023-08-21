package com.backend.topcariving.domain.dto.option.response.estimation;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "견적 요약 보기에 표시되는 항목들, key-value로 매칭되어있음")
public class SummaryResponseDTO {

	private Map<String, List<OptionSummaryDTO>> options;
}
