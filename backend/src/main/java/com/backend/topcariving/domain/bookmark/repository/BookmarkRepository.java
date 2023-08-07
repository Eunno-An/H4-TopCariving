package com.backend.topcariving.domain.bookmark.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.bookmark.entity.Bookmark;

@Repository
public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
}
