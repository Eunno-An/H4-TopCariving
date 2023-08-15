package com.backend.topcariving.domain.archive.repository;

import java.util.Optional;

import com.backend.topcariving.domain.archive.entity.CarArchiving;

public interface CarArchivingRepository {

	CarArchiving save(CarArchiving carArchiving);

	boolean existsByUserIdAndArchivingId(Long userId, Long archivingId);

	void updateIsCompleteByArchivingId(Long archivingId, Boolean isComplete);

	Optional<CarArchiving> findById(Long archivingId);
}
