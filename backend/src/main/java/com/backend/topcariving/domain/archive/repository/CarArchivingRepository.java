package com.backend.topcariving.domain.archive.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.archive.entity.CarArchiving;

@Repository
public interface CarArchivingRepository extends CrudRepository<CarArchiving, Long> {

	boolean existsByUserIdAndArchivingId(Long userId, Long archivingId);
}
