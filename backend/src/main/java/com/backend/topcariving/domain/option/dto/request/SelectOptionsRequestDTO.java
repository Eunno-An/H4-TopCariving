package com.backend.topcariving.domain.option.dto.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "선택한 옵션이 여러 개일 때 받는 RequestDTO")
public class SelectOptionsRequestDTO {

	@Schema(description = "User ID", example = "1")
	private Long userId;

	@Schema(description = "선택한 옵션의 ID들")
	private List<Long> ids;

	@Schema(description = "차량 아카이빙 ID", example = "1")
	private Long archivingId;

}
