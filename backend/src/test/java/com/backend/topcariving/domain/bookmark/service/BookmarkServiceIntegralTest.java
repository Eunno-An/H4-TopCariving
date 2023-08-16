package com.backend.topcariving.domain.bookmark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.dto.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.bookmark.entity.Bookmark;
import com.backend.topcariving.domain.bookmark.repository.BookmarkRepository;

@SpringBootTest
@Transactional
class BookmarkServiceIntegralTest extends TestSupport {

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private BookmarkRepository  bookmarkRepository;

	@Test
	void 토글은_제대로_변경이_되어야_한다() {
		// given
		BookmarkRequestDTO bookmarkRequestDTOHaveTrue = new BookmarkRequestDTO(1L, 2L, true);
		BookmarkRequestDTO bookmarkRequestDTOHaveFalse = new BookmarkRequestDTO(1L, 2L, false);

		// when, then
		Boolean isAlive = bookmarkService.toggleBookmark(bookmarkRequestDTOHaveTrue);
		Bookmark bookmark = bookmarkRepository.findByUserIdAndArchivingId(2L, 1L).get();
		softAssertions.assertThat(isAlive).as("제대로 변경된 값을 반환하는지 테스트").isTrue();
		softAssertions.assertThat(bookmark.getIsAlive()).as("실제로 저장된 값도 True인지 확인").isTrue();

		isAlive = bookmarkService.toggleBookmark(bookmarkRequestDTOHaveFalse);
		bookmark = bookmarkRepository.findByUserIdAndArchivingId(2L, 1L).get();
		softAssertions.assertThat(isAlive).as("제대로 변경된 값을 반환하는지 테스트").isFalse();
		softAssertions.assertThat(bookmark.getIsAlive()).as("실제로 저장된 값도 False인지 확인").isFalse();
	}


}