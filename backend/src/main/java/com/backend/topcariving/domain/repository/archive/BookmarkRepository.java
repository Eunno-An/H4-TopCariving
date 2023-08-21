package com.backend.topcariving.domain.repository.archive;

import java.util.Optional;

import com.backend.topcariving.domain.entity.archive.Bookmark;

public interface BookmarkRepository {
	
   Optional<Bookmark> findByUserIdAndArchivingId(Long userId, Long archivingId);

   Bookmark save(Bookmark bookmark);

   void updateIsAliveByBookmarkId(Boolean isAlive, Long bookmarkId);

   void updateIsAliveByUserIdAndArchivingId(Boolean isAlive, Long userId, Long archivingId);

   Optional<Bookmark> findById(Long bookmarkId);
}
