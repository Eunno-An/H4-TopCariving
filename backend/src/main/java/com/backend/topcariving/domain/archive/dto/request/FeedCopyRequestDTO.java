package com.backend.topcariving.domain.archive.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "피드의 차량을 복사하기 위해서 필요한 DTO")
public class FeedCopyRequestDTO {

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "아카이빙 ID", example = "1")
	private Long archivingId;

}
