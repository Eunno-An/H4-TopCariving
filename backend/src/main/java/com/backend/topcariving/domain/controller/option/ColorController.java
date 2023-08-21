package com.backend.topcariving.domain.controller.option;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.dto.option.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.dto.option.request.color.BothColorRequestDTO;
import com.backend.topcariving.domain.dto.option.response.color.BothColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.InteriorColorResponseDTO;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.option.ColorService;
import com.backend.topcariving.domain.service.option.TrimService;
import com.backend.topcariving.global.auth.annotation.Login;
import com.backend.topcariving.global.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "내 차 만들기 - 색상")
@RequestMapping("/api/options/colors")
@RestController
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class ColorController {

	private final ColorService colorService;
	private final TrimService trimService;

	@GetMapping("/exteriors")
	@Operation(summary = "외장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 외장 색상 옵션을 반환한다")
	public List<ExteriorColorResponseDTO> getExteriorColors() {
		return colorService.getExteriorColors();
	}

	@PostMapping("/exteriors")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "외장 색상 옵션 저장", description = "내 차 만들기에서 외장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveExteriorColor(@Parameter(hidden = true) @Login Long userId, @RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {
		final Long archivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.EXTERIOR_COLOR);
		return new SuccessResponse<>(archivingId);
	}

	@GetMapping("/interiors")
	@Operation(summary = "내장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 내장 색상 옵션을 반환한다")
	public List<InteriorColorResponseDTO> getInteriorColors() {
		return colorService.getInteriorColors();
	}

	@PostMapping("/interiors")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "내장 색상 옵션 저장", description = "내 차 만들기에서 내장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveInteriorColor(@Parameter(hidden = true) @Login Long userId, @RequestBody SelectOptionRequestDTO selectOptionRequestDTO) {
		final Long archivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.INTERIOR_COLOR);
		return new SuccessResponse<>(archivingId);
	}

	@GetMapping("/both")
	@Operation(summary = "외장, 내장 색상 옵션 전체 반환", description = "내 차 만들기에서 모든 외장, 내장 색상 옵션을 반환한다")
	public BothColorResponseDTO getBothColors() {
		return colorService.getBothResponseDTO();
	}

	@PostMapping("/both")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "외장, 내장 색상 옵션 저장", description = "내 차 만들기에서 외장, 내장 색상을 선택한 값을 저장하고, 아카이빙 PK 값을 반환한다")
	public SuccessResponse<Long> saveBothColor(@Parameter(hidden = true) @Login Long userId, @RequestBody BothColorRequestDTO bothColorRequestDTO) {
		final Long archivingId = bothColorRequestDTO.getArchivingId();
		final Long interiorColorOptionId = bothColorRequestDTO.getInteriorColorOptionId();
		final Long exteriorColorOptionId = bothColorRequestDTO.getExteriorColorOptionId();

		trimService.saveOption(userId, new SelectOptionRequestDTO(interiorColorOptionId, archivingId), CategoryDetail.INTERIOR_COLOR);
		trimService.saveOption(userId, new SelectOptionRequestDTO(exteriorColorOptionId, archivingId), CategoryDetail.EXTERIOR_COLOR);
		return new SuccessResponse<>(archivingId);
	}

}
