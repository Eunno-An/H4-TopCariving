package com.backend.topcariving.domain.bookmark.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.bookmark.entity.Bookmark;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Bookmark> findByUserIdAndArchivingId(final Long userId, final Long archivingId) {
		String sql = "SELECT * FROM BOOKMARK WHERE user_id = ? AND archiving_id = ?";

		final List<Bookmark> result = jdbcTemplate.query(sql, bookmarkMapper(), userId, archivingId);

		if (result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public Bookmark save(final Bookmark bookmark) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("BOOKMARK").usingGeneratedKeyColumns("bookmark_id");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", bookmark.getUserId());
		parameters.put("archiving_id", bookmark.getArchivingId());
		parameters.put("is_alive", bookmark.getIsAlive());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		return new Bookmark(key.longValue(), bookmark.getIsAlive(), bookmark.getUserId(), bookmark.getArchivingId());
	}

	@Override
	public void updateIsAliveByBookmarkId(final Boolean isAlive, final Long bookmarkId) {
		String sql = "UPDATE BOOKMARK SET is_alive = ? WHERE bookmark_id = ?";

		jdbcTemplate.update(sql, isAlive, bookmarkId);
	}

	@Override
	public void updateIsAliveByUserIdAndArchivingId(Boolean isAlive, Long userId, Long archivingId) {
		String sql = "UPDATE BOOKMARK SET is_alive = ? WHERE user_id = ? AND archiving_id = ?;";

		jdbcTemplate.update(sql, isAlive, userId, archivingId);
	}

	@Override
	public Optional<Bookmark> findById(Long bookmarkId) {
		String sql = "SELECT * FROM BOOKMARK WHERE bookmark_id = ?";

		List<Bookmark> result = jdbcTemplate.query(sql, bookmarkMapper(), bookmarkId);

		if (result.isEmpty()) {
			return Optional.empty();
		}

		return Optional.ofNullable(result.get(0));
	}

	private RowMapper<Bookmark> bookmarkMapper() {
		return (rs, rowNum) ->
			new Bookmark(
				rs.getLong("bookmark_id"),
				rs.getBoolean("is_alive"),
				rs.getLong("user_id"),
				rs.getLong("archiving_id")
			);
	}
}
