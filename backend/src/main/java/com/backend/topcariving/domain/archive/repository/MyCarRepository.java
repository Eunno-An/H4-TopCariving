package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.archive.entity.MyCar;

@Repository
public interface MyCarRepository extends CrudRepository<MyCar, Long> {

	Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);

	List<MyCar> findByArchivingId(Long archivingId);
}
