package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.archive.dto.CarDTO;
import com.backend.topcariving.domain.archive.entity.CarArchiving;

public interface CarArchivingRepository {

	CarArchiving save(CarArchiving carArchiving);

	boolean existsByUserIdAndArchivingId(Long userId, Long archivingId);

	boolean existsByArchivingId(Long archivingId);

	void updateIsCompleteByArchivingId(Long archivingId, Boolean isComplete);

	void updateIsAliveByArchivingId(Boolean isAlive, Long archivingId);

	Optional<CarArchiving> findById(Long archivingId);

	List<CarArchiving> findByCarOptionIdsAndArchivingTypes(List<Long> archivingIds, List<String> archivingTypes, Integer pageNumber, Integer pageSize);

	List<CarArchiving> findByArchivingTypes(List<String> archivingTypes, Integer pageNumber, Integer pageSize);

	List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSize(Long userId, Integer offset, Integer pageSize);

	List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrue(Long userId, Integer pageNumber, Integer pageSize);
}
