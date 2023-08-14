package com.backend.topcariving.domain.option.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingResponseDTO;
import com.backend.topcariving.domain.option.dto.response.estimation.SummaryResponseDTO;
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

	@PostMapping("/")
	@Operation(summary = "견적 요약보기에서 변경사항 저장", description = "견적 요약 보기")
	public SuccessResponse<Long> estimationChange(@RequestBody EstimationChangeRequestDTO request) {

		final Long archivingId = estimationService.changeOptions(request);
		return new SuccessResponse<>(archivingId);
	}

	@GetMapping("/summary/{userId}/{archivingId}")
	@Operation(summary = "내 차 요약 보기 확인", description = "아카이빙 ID를 사용해서 현재 선택한 항목들의 요약보기를 진행한다")
	public SummaryResponseDTO summary(@PathVariable Long userId, @PathVariable Long archivingId) {
		return estimationService.summary(userId, archivingId);
	}

}
