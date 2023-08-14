package com.backend.topcariving.domain.archive.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "북마크를 하기위한 정보를 받는 DTO")
public class BookmarkRequestDTO {

	@Schema(description = "저장하고자 하는 아카이빙 ID", example = "1")
	private Long archivingId;

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "북마크의 상태", example = "true")
	private Boolean isBookmarked;
}
