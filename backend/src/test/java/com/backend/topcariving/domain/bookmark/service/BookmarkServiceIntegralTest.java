package com.backend.topcariving.domain.bookmark.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.dto.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.bookmark.entity.Bookmark;
import com.backend.topcariving.domain.bookmark.repository.BookmarkRepository;

@SpringBootTest
@Transactional
class BookmarkServiceIntegralTest extends TestSupport {

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private BookmarkRepository  bookmarkRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Test
	void 토글은_제대로_변경이_되어야_한다() {
		// given
		BookmarkRequestDTO bookmarkRequestDTOHaveTrue = new BookmarkRequestDTO(1L, true);
		BookmarkRequestDTO bookmarkRequestDTOHaveFalse = new BookmarkRequestDTO(1L, false);

		// when, then
		Boolean isAlive = bookmarkService.toggleBookmark(2L, bookmarkRequestDTOHaveTrue);
		Bookmark bookmark = bookmarkRepository.findByUserIdAndArchivingId(2L, 1L).get();
		softAssertions.assertThat(isAlive).as("제대로 변경된 값을 반환하는지 테스트").isTrue();
		softAssertions.assertThat(bookmark.getIsAlive()).as("실제로 저장된 값도 True인지 확인").isTrue();

		isAlive = bookmarkService.toggleBookmark(2L, bookmarkRequestDTOHaveFalse);
		bookmark = bookmarkRepository.findByUserIdAndArchivingId(2L, 1L).get();
		softAssertions.assertThat(isAlive).as("제대로 변경된 값을 반환하는지 테스트").isFalse();
		softAssertions.assertThat(bookmark.getIsAlive()).as("실제로 저장된 값도 False인지 확인").isFalse();
	}

	@Test
	void 마이카이빙의_피드에서_저장한_차량_하나를_삭제할_수_있어야_한다() {
		// given
		Long userId = 1L;
		Long archivingId = 2L;

		// when
		Long deletedArchivingId = bookmarkService.deleteMyArchiving(userId, archivingId);

		// then
		Bookmark bookmark = bookmarkRepository.findByUserIdAndArchivingId(userId, archivingId).get();
		Assertions.assertThat(bookmark.getIsAlive()).isFalse();
	}

	@Test
	void 마이카이빙의_피드에서_저장한_차량_중_삭제한_차량을_되돌릴_수_있어야_한다() {
		// given
		Long userId = 1L;
		Long archivingId = 2L;
		Long deletedArchivingId = bookmarkService.deleteMyArchiving(userId, archivingId);

		// when
		Long restoredArchivingId = bookmarkService.restoreMyArchiving(userId, deletedArchivingId);

		// then
		Bookmark bookmark = bookmarkRepository.findByUserIdAndArchivingId(userId, restoredArchivingId).get();
		Assertions.assertThat(bookmark.getIsAlive()).isTrue();
	}

}