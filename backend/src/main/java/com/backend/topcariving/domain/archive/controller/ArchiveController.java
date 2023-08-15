package com.backend.topcariving.domain.archive.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.archive.dto.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.archive.dto.request.FeedCopyRequestDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.CreatedCarDTO;
import com.backend.topcariving.global.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "아카이빙")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/archiving")
public class ArchiveController {

	@GetMapping("/")
	@Operation(summary = "아카이빙 메인 전체 결과 확인", description = "서비스 사용자들이 시승/구매한 차량 정보 조회")
	public ArchiveResponseDTO archivingSearch(@RequestParam(required = false) List<Long> optionIds) {
		return null;
	}

	@GetMapping("/created-cars")
	@Operation(summary = "마이카이빙 내가 만든 차량 목록 조회", description = "서비스 사용자들이 만든 차량 정보 조회")
	public List<CreatedCarDTO> getCreatedCars(@RequestParam Integer offset, @RequestParam Integer pageSize) {
		return null;
	}

	@GetMapping("/feeds")
	@Operation(summary = "마이카이빙 피드에서 저장한 차량 목록 조회", description = "서비스 사용자들이 피드에서 저장한 차량 정보 조회")
	public List<ArchiveFeedDTO> getFeedCars(@RequestParam Integer offset, @RequestParam Integer pageSize) {
		return null;
	}

	@PostMapping("/feeds")
	@Operation(summary = "마이카이빙 피드에 있는 차량을 내가 만든 차량으로 복사", description = "마이카이빙 피드에서 '이 차량으로 내 차 만들기' 버튼을 누르면 실행되는 연산(반환 값은 새로 만들어진 아카이빙 ID입니다)")
	public SuccessResponse<Long> saveFeedToCreatedCar(@RequestBody FeedCopyRequestDTO feedCopyRequestDTO) {
		return null;
	}

	@GetMapping("/details/{archivingId}")
	@Operation(summary = "차량 세부 정보 조회", description = "아카이빙 및 마이아카이빙에서 선택한 차량의 세부 정보 조회")
	public ArchiveDetailResponseDTO getDetailsCars(@PathVariable Long archivingId) {
		return null;
	}

	@PostMapping("/feeds/bookmarks")
	@Operation(summary = "차량 북마크 추가 및 삭제", description = "차량을 피드에서 저장한 차량 목록에 저장 및 삭제")
	public SuccessResponse<Boolean> toggleBookmark(@RequestBody BookmarkRequestDTO bookmarkRequestDTO) {
		return null;
	}

	@DeleteMapping("/created-cars/{userId}/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 차량 삭제", description = "마이카이빙에 있는 차량 삭제하고 삭제한 차량의 아카이빙 아이디 반환")
	public SuccessResponse<Long> deleteMyArchiving(@PathVariable Long userId, @PathVariable Long archivingId) {
		return null;
	}

	@PostMapping("/created-cars/{userId}/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 차량 삭제 되돌리기", description = "마이카이빙에서 삭제한 차량을 되돌리고 아카이빙 아이디 반환")
	public SuccessResponse<Long> restoreMyArchiving(@PathVariable Long userId, @PathVariable Long archivingId) {
		return null;
	}
}
