package com.backend.topcariving.domain.bookmark.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.bookmark.entity.Bookmark;

@JdbcTest
class BookmarkRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private BookmarkRepositoryImpl bookmarkRepository;

	@BeforeEach
	void setUp() {
		bookmarkRepository = new BookmarkRepositoryImpl(jdbcTemplate);
	}

	@Test
	void findIsAliveByUserIdAndArchivingId() {
		// given
		boolean isAlive = true;
		Long userId = 2L;
		Long archivingId = 2L;

		Bookmark bookmark = new Bookmark(null, isAlive, userId, archivingId);
		bookmark = bookmarkRepository.save(bookmark);

		// when
		Optional<Bookmark> findBookmark = bookmarkRepository.findByUserIdAndArchivingId(userId,
			archivingId);
		softAssertions.assertThat(findBookmark).as("찾은 데이터가 존재하는지").isPresent();
		softAssertions.assertThat(findBookmark.get()).as("저장한 데이터와 같은지").usingRecursiveComparison().isEqualTo(bookmark);
	}

	@Test
	void save() {
		// given
		boolean isAlive = true;
		Long userId = 2L;
		Long archivingId = 2L;

		Bookmark bookmark = new Bookmark(null, isAlive, userId, archivingId);

		// when
		bookmark = bookmarkRepository.save(bookmark);

		// then
		Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getBookmarkId());
		softAssertions.assertThat(findBookmark).isPresent();
		softAssertions.assertThat(findBookmark.get()).usingRecursiveComparison().isEqualTo(bookmark);
	}

	@Test
	void updateIsAliveByBookmarkId() {
		// given
		Long userId = 2L;
		Long archivingId = 2L;

		Bookmark bookmark = new Bookmark(null, false, userId, archivingId);
		bookmark = bookmarkRepository.save(bookmark);
		// when

		bookmarkRepository.updateIsAliveByBookmarkId(true, bookmark.getBookmarkId());
		// then
		Optional<Bookmark> findBookmark = bookmarkRepository.findById(bookmark.getBookmarkId());

		Assertions.assertThat(findBookmark.get().getIsAlive()).isTrue();
	}
}