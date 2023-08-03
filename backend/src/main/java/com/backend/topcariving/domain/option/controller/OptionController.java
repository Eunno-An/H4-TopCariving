package com.backend.topcariving.domain.option.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.option.dto.OptionResponseDTO;
import com.backend.topcariving.domain.option.service.OptionService;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="내 차 만들기 - 옵션")
@RequestMapping("/api/options")
@RestController
@RequiredArgsConstructor
public class OptionController {

	private final OptionService optionService;

	@GetMapping("/trim/model")
	@Operation(summary = "트림 옵션 전체 반환", description = "내 차 만들기에서 트림 옵션 전체를 반환한다.")
	// @Parameter(name = "userId", description = "유저 아이디")
	public List<OptionResponseDTO> getModels() {
		return optionService.getModels();
	}

	@PostMapping("/trim")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(responseCode = "201", description = "성공하면, 아카이빙 PK 값을 반환함")
	@Operation(summary = "트림 옵션 저장", description = "내 차 만들기에서 트림을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	@Parameter(name = "userId", description = "유저 아이디")
	public SuccessResponse<Long> saveTrim(@RequestParam String userId) {

		return null;
	}
}
