package com.backend.topcariving.domain.option.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingResponseDTO;
import com.backend.topcariving.domain.option.dto.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ModifyOptionResponseDTO;
import com.backend.topcariving.domain.option.service.EstimationService;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "내 차 만들기 - 견적 요약보기")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/options/estimations")
public class EstimationController {

	private final EstimationService estimationService;

	@GetMapping("/")
	@Operation(summary = "내 차 최종 결과 확인", description = "아카이빙 ID를 사용해 해당 차량의 최종 결과를 반환한다")
	public ArchivingResponseDTO getArchivingResult(@RequestParam Long userId, @RequestParam Long archivingId) {

		return estimationService.getArchivingResult(userId, archivingId);
	}

	@PostMapping("/modify")
	@Operation(summary = "견적 요약보기에서 옵션 변경", description = "해당 차량의 최종 결과와 변경하고자 하는 옵션 ID를 반환한다")
	public ModifyOptionResponseDTO modifyCarOption(@RequestParam Long archivingId, @RequestParam Long carOptionId) {

		return null;
	}

	@PostMapping("/")
	@Operation(summary = "견적 요약보기에서 변경사항 저장", description = "견적 요약 보기")
	public SuccessResponse<Long> estimationChange(@RequestBody EstimationChangeRequestDTO request) {

		return null;
	}
}
