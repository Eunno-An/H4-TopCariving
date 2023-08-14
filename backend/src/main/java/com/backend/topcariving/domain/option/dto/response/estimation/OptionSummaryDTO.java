package com.backend.topcariving.domain.option.dto.response.estimation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "옵션들을 저장하는 DTO")
public class OptionSummaryDTO {

	@Schema(description = "옵션의 이름")
	private String name;

	@Schema(description = "카테고리  ex) 트림, 색상 등등")
	private String category;

	@Schema(description = "카테고리 세부내역 ex) 모델, 엔진 등등")
	private String categoryDetail;

	@Schema(description = "옵션의 가격")
	private Integer price;

	@Schema(description = "옵션의 사진 / 색상만 해당 내용이 들어감")
	private String photoUrl;
}
