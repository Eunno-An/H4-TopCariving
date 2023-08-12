package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.archive.entity.MyCar;

public interface MyCarRepository {

	MyCar save(MyCar myCar);

	Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);

	List<MyCar> findByArchivingId(Long archivingId);
}
