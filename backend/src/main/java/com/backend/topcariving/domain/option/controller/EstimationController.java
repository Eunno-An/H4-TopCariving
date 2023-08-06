package com.backend.topcariving.domain.option.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.EstimationChangeRequest;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "내 차 만들기 - 견적 요약보기")
@RestController
@RequestMapping("/api/options/estimations")
public class EstimationController {

	@PostMapping("/")
	@Operation(summary = "견적 요약보기에서 변경사항 저장", description = "견적 요약 보기")
	public SuccessResponse<Long> estimationChange(@RequestBody EstimationChangeRequest request) {

		return null;
	}
}
