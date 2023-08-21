package com.backend.topcariving.domain.controller.archive;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.topcariving.domain.dto.bookmark.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.CreatedCarDTO;
import com.backend.topcariving.domain.service.archive.ArchiveService;
import com.backend.topcariving.domain.service.archive.BookmarkService;
import com.backend.topcariving.global.auth.annotation.Login;
import com.backend.topcariving.global.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "아카이빙")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/archiving")
public class ArchiveController {

	private final ArchiveService archiveService;
	private final BookmarkService bookmarkService;

	@GetMapping("/result")
	@Operation(summary = "아카이빙 메인 전체 결과 확인", description = "서비스 사용자들이 시승/구매한 차량 정보 조회")
	public ArchiveResponseDTO archivingSearch(@RequestParam(required = false) List<Long> optionIds, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return archiveService.archivingSearch(optionIds, pageNumber, pageSize);
	}

	@GetMapping("/created-cars")
	@Operation(summary = "마이카이빙 내가 만든 차량 목록 조회", description = "서비스 사용자들이 만든 차량 정보 조회")
	public List<CreatedCarDTO> getCreatedCars(@Parameter(hidden = true) @Login Long userId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return archiveService.getCreatedCars(userId, pageNumber, pageSize);
	}

	@GetMapping("/feeds")
	@Operation(summary = "마이카이빙 피드에서 저장한 차량 목록 조회", description = "서비스 사용자들이 피드에서 저장한 차량 정보 조회")
	public List<ArchiveFeedDTO> getFeedCars(@Parameter(hidden = true) @Login Long userId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return archiveService.getFeedCars(userId, pageNumber, pageSize);
	}

	@PostMapping("/feeds/{archivingId}")
	@Operation(summary = "마이카이빙 피드에 있는 차량을 내가 만든 차량으로 복사", description = "마이카이빙 피드에서 '이 차량으로 내 차 만들기' 버튼을 누르면 실행되는 연산(반환 값은 새로 만들어진 아카이빙 ID입니다)")
	public SuccessResponse<Long> saveFeedToCreatedCar(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		Long copyArchivingId = archiveService.saveFeedToCreatedCar(userId, archivingId);
		return new SuccessResponse<>(copyArchivingId);
	}

	@GetMapping("/details/{archivingId}")
	@Operation(summary = "차량 세부 정보 조회", description = "아카이빙 및 마이아카이빙에서 선택한 차량의 세부 정보 조회")
	public ArchiveDetailResponseDTO getDetailsCars(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		return archiveService.getDetailsCars(userId, archivingId);
	}

	@PostMapping("/feeds/bookmarks")
	@Operation(summary = "차량 북마크 추가 및 삭제", description = "차량을 피드에서 저장한 차량 목록에 저장 및 삭제")
	public SuccessResponse<Boolean> toggleBookmark(@Parameter(hidden = true) @Login Long userId, @RequestBody BookmarkRequestDTO bookmarkRequestDTO) {
		Boolean isAlive = bookmarkService.toggleBookmark(userId, bookmarkRequestDTO);
		return new SuccessResponse<>(isAlive);
	}

	@DeleteMapping("/created-cars/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 내가 만든 차량 삭제", description = "마이카이빙에 있는 내가 만든 차량 삭제하고 삭제한 차량의 아카이빙 아이디 반환")
	public SuccessResponse<Long> deleteMyArchivingCreatedCar(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		Long deletedArchivingId = archiveService.deleteMyArchiving(userId, archivingId);
		return new SuccessResponse<>(deletedArchivingId);
	}

	@PostMapping("/created-cars/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 내가 만든 차량 삭제 되돌리기", description = "마이카이빙에서 삭제한 내가 만든 차량을 되돌리고 아카이빙 아이디 반환")
	public SuccessResponse<Long> restoreMyArchivingCreatedCar(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		Long restoredArchivingId = archiveService.restoreMyArchiving(userId, archivingId);
		return new SuccessResponse<>(restoredArchivingId);
	}

	@DeleteMapping("/feed-cars/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 피드에서 저장한 차량 삭제", description = "마이카이빙에 있는 피드에서 저장한 차량 삭제하고 삭제한 차량의 아카이빙 아이디 반환")
	public SuccessResponse<Long> deleteMyArchivingFeed(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		Long deletedArchivingId = bookmarkService.deleteMyArchiving(userId, archivingId);
		return new SuccessResponse<>(deletedArchivingId);
	}

	@PostMapping("/feed-cars/{archivingId}")
	@Operation(summary = "마이카이빙에 있는 피드에서 저장한 차량 삭제 되돌리기", description = "마이카이빙에서 삭제한 피드에서 저장한차량을 되돌리고 아카이빙 아이디 반환")
	public SuccessResponse<Long> restoreMyArchivingFeed(@Parameter(hidden = true) @Login Long userId, @PathVariable Long archivingId) {
		Long restoredArchivingId = bookmarkService.restoreMyArchiving(userId, archivingId);
		return new SuccessResponse<>(restoredArchivingId);
	}
}
