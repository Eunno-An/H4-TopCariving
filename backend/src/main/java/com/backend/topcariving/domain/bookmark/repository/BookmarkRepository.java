package com.backend.topcariving.domain.bookmark.repository;

import java.util.Optional;

import com.backend.topcariving.domain.bookmark.entity.Bookmark;

public interface BookmarkRepository {
	
   Optional<Bookmark> findByUserIdAndArchivingId(Long userId, Long archivingId);

   Bookmark save(Bookmark bookmark);

   void updateIsAliveByBookmarkId(Boolean isAlive, Long bookmarkId);

   Optional<Bookmark> findById(Long bookmarkId);
}
