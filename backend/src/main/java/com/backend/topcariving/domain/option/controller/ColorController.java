package com.backend.topcariving.domain.option.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.color.BothColorResponseDTO;
import com.backend.topcariving.domain.option.dto.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.option.dto.color.InteriorColorResponseDTO;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "내 차 만들기 - 색상")
@RequestMapping("/api/options/colors")
@RestController
@RequiredArgsConstructor
public class ColorController {

	@GetMapping("/exteriors")
	@Operation(summary = "외장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 외장 색상 옵션을 반환한다")
	public List<ExteriorColorResponseDTO> getExteriorColors() {
		return null;
	}

	@PostMapping("/exteriors")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "외장 색상 옵션 저장", description = "내 차 만들기에서 외장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveExteriorColor(@RequestParam Long carOptionId) {
		return null;
	}

	@GetMapping("/interiors")
	@Operation(summary = "내장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 내장 색상 옵션을 반환한다")
	public List<InteriorColorResponseDTO> getInteriorColors() {
		return null;
	}

	@PostMapping("/interiors")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "내장 색상 옵션 저장", description = "내 차 만들기에서 내장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveInteriorColor(@RequestParam Long carOptionId) {
		return null;
	}

	@GetMapping("/both")
	@Operation(summary = "외장, 내장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 외장, 내장 색상 옵션을 반환한다")
	public BothColorResponseDTO getBothColors() {
		return null;
	}

	@PostMapping("/both")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "외장, 내장 색상 옵션 저장", description = "내 차 만들기에서 외장, 내장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveBothColor(@RequestParam Long exteriorColorOptionId, @RequestParam Long interiorColorOptionId) {
		return null;
	}

}
