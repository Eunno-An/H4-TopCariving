package com.backend.topcariving.domain.repository.archive;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.dto.projection.CarDTO;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;

public interface CarArchivingRepository {

	CarArchiving save(CarArchiving carArchiving);

	boolean existsByUserIdAndArchivingId(Long userId, Long archivingId);

	boolean existsByArchivingId(Long archivingId);

	void updateIsCompleteByArchivingId(Long archivingId, Boolean isComplete);

	void updateIsAliveByArchivingId(Boolean isAlive, Long archivingId);

	Optional<CarArchiving> findById(Long archivingId);

	List<CarArchiving> findByCarOptionIdsAndArchivingTypes(List<Long> archivingIds, List<ArchivingType> archivingTypes, Integer pageNumber, Integer pageSize);

	List<CarArchiving> findByArchivingTypes(List<ArchivingType> archivingTypes, Integer pageNumber, Integer pageSize);

	List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSize(Long userId, Integer offset, Integer pageSize);

	List<CarDTO> findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrue(Long userId, Integer pageNumber, Integer pageSize);
}
