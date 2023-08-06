package com.backend.topcariving.domain.option.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.BasicOptionDTO;
import com.backend.topcariving.domain.option.dto.OptionRequestDTO;
import com.backend.topcariving.domain.option.dto.SelectionResponseDTO;
import com.backend.topcariving.domain.option.dto.TrimResponseDTO;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "내 차 만들기 - 선택, 기본, H Genuine, N performance 옵션")
@RequestMapping("/api/options")
@RestController
public class OptionController {


	@GetMapping("/selections")
	@Operation(summary = "선택 옵션 전체 반환", description = "내 차 만들기에서 선택 옵션 전체를 반환한다")
	public List<TrimResponseDTO> getSelections() {

		return null;
	}

	@GetMapping("/details")
	@Operation(summary = "선택 옵션 디테일 반환", description = "내 차 만들기에서 선택한 옵션의 세부 내역을 반환한다")
	public SelectionResponseDTO getSelectionDetails(@RequestParam Long carOptionId) {

		return null;
	}

	@PostMapping("/selections")
	@Operation(summary = "선택 옵션 저장", description = "내 차 만들기에서 선택한 옵션들의 값을 저장하고, 차량 아카이브 PK 값을 반환함")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse<Long> saveSelectionOptions(@RequestBody OptionRequestDTO requestDTO) {

		return null;
	}

	@GetMapping("/basic")
	@Operation(summary = "기본 옵션 전체 반환", description = "내 차 만들기에서 기본 옵션 전체를 반환한다")
	public List<BasicOptionDTO> getBasics() {

		return null;
	}

	@GetMapping("/genuine")
	@Operation(summary = "H Genuine Accessories 전체 반환", description = "내 차 만들기에서 H Genuine Accessories에 대한 옵션 전체를 반환한다")
	public List<TrimResponseDTO> getGenuine() {

		return null;
	}

	@PostMapping("/genuine")
	@Operation(summary = "H Genuine Accessories 저장", description = "내 차 만들기에서 선택한 옵션들의 값을 저장하고, 차량 아카이브 PK 값을 반환함")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse<Long> saveGenuine(@RequestBody OptionRequestDTO requestDTO) {

		return null;
	}

	@GetMapping("/performances")
	@Operation(summary = "N Performance 전체 반환", description = "내 차 만들기에서 N Performance에 대한 옵션 전체를 반환한다")
	public List<TrimResponseDTO> getPerformance() {

		return null;
	}

	@PostMapping("/performances")
	@Operation(summary = "N Performance 저장", description = "내 차 만들기에서 선택한 옵션들의 값을 저장하고, 차량 아카이브 PK 값을 반환함")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse<Long> saveGenuine(@RequestParam Long carOptionId) {

		return null;
	}
}
