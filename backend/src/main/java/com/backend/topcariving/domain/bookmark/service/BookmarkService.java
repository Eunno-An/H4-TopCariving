package com.backend.topcariving.domain.bookmark.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.dto.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.bookmark.entity.Bookmark;
import com.backend.topcariving.domain.bookmark.repository.BookmarkRepository;
import com.backend.topcariving.global.utils.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	private final Validator validator;

	@Transactional
	public Boolean toggleBookmark(BookmarkRequestDTO bookmarkRequestDTO) {
		final Boolean isBookmarked = bookmarkRequestDTO.getIsBookmarked();
		final Long userId = bookmarkRequestDTO.getUserId();
		final Long archivingId = bookmarkRequestDTO.getArchivingId();

		final Optional<Bookmark> findBookmark = bookmarkRepository.findByUserIdAndArchivingId(
			userId,
			archivingId);

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

}
