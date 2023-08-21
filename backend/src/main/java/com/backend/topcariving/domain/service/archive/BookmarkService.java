package com.backend.topcariving.domain.service.archive;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.dto.bookmark.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.entity.archive.Bookmark;
import com.backend.topcariving.domain.repository.archive.BookmarkRepository;
import com.backend.topcariving.global.utils.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	private final Validator validator;

	@Transactional
	public Boolean toggleBookmark(Long userId, BookmarkRequestDTO bookmarkRequestDTO) {
		final Boolean isBookmarked = bookmarkRequestDTO.getIsBookmarked();
		final Long archivingId = bookmarkRequestDTO.getArchivingId();

		final Optional<Bookmark> findBookmark = bookmarkRepository.findByUserIdAndArchivingId(userId, archivingId);

		return saveBookmark(isBookmarked, userId, archivingId, findBookmark);
	}

	private Boolean saveBookmark(final Boolean isBookmarked, final Long userId, final Long archivingId,
		final Optional<Bookmark> findBookmark) {
		if (findBookmark.isEmpty()) {
			final Bookmark bookmark = new Bookmark(null, isBookmarked, userId, archivingId);
			bookmarkRepository.save(bookmark);
			return isBookmarked;
		}

		final Long bookmarkId = findBookmark.get().getBookmarkId();
		bookmarkRepository.updateIsAliveByBookmarkId(isBookmarked, bookmarkId);
		return isBookmarked;
	}

	public Long deleteMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(false, userId, archivingId);
	}

	public Long restoreMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(true, userId, archivingId);
	}

	private Long toggleMyArchiving(Boolean isAlive, Long userId, Long archivingId) {
		validator.verifyArchivingId(archivingId);

		bookmarkRepository.updateIsAliveByUserIdAndArchivingId(isAlive, userId, archivingId);
		return archivingId;
	}

}
