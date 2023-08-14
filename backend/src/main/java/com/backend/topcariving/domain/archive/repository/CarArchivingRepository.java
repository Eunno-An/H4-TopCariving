package com.backend.topcariving.domain.archive.repository;

import com.backend.topcariving.domain.archive.entity.CarArchiving;

public interface CarArchivingRepository {

	CarArchiving save(CarArchiving carArchiving);

	boolean existsByUserIdAndArchivingId(Long userId, Long archivingId);
}
