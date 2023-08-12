package com.backend.topcariving.domain.option.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.dto.response.engine.EngineResponseDTO;
import com.backend.topcariving.domain.option.dto.response.model.ModelResponseDTO;
import com.backend.topcariving.domain.option.dto.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.service.TrimService;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "내 차 만들기 - 옵션")
@RequestMapping("/api/options/trims")
@RestController
@RequiredArgsConstructor
public class TrimController {

	private final TrimService trimService;

	@GetMapping("/models")
	@Operation(summary = "모델 옵션 전체 반환", description = "내 차 만들기에서 모델 옵션 전체를 반환한다.")
	public List<ModelResponseDTO> getModels() {

		return trimService.getModels();
	}

	@PostMapping("/models")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201", description = "성공하면, 아카이빙 PK 값을 반환함")
	@Operation(summary = "모델 옵션 저장", description = "내 차 만들기에서 모델을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveModel(@RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {

		return new SuccessResponse<>(trimService.saveModel(selectOptionRequestDTO));
	}

	@GetMapping("/engines")
	@Operation(summary = "엔진 옵션 전체 반환", description = "내 차 만들기에서 모든 엔진 옵션을 반환한다")
	public List<EngineResponseDTO> getEngines() {

		return trimService.getEngines();
	}

	@PostMapping("/engines")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "엔진 옵션 저장", description = "내 차 만들기에서 엔진을 선택한 값을 저장하고, 차량 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveEngine(@RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {

		return new SuccessResponse<>(trimService.saveOption(selectOptionRequestDTO, CategoryDetail.ENGINE));
	}

	@GetMapping("/body-types")
	@Operation(summary = "바디 타입 전체 반환", description = "내 차 만들기에서 바디 타입 반환")
	public List<OptionResponseDTO> getBodyTypes() {

		return trimService.getOptions(CategoryDetail.BODY_TYPE);
	}

	@PostMapping("/body-types")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "바디 타입 저장", description = "내 차 만들기에서 바디 타입 선택한 값을 저장하고, 차량 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveBodyTypes(@RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {

		return new SuccessResponse<>(trimService.saveOption(selectOptionRequestDTO, CategoryDetail.BODY_TYPE));
	}

	@GetMapping("/driving-methods")
	@Operation(summary = "구동 방식 전체 반환", description = "내 차 만들기에서 구동 방식 반환")
	public List<OptionResponseDTO> getDrivingMethods() {

		return trimService.getOptions(CategoryDetail.DRIVING_METHOD);
	}

	@PostMapping("/driving-methods")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "구동 방식 저장", description = "내 차 만들기에서 구동 방식 선택한 값을 저장하고, 차량 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveDrivingMethod(@RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {

		final Long archivingId = trimService.saveOption(selectOptionRequestDTO, CategoryDetail.DRIVING_METHOD);
		return new SuccessResponse<>(archivingId);
	}
}
