package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.option.entity.Position;

public interface PositionRepository {

	Optional<Position> findByCarOptionId(Long carOptionId);

	List<Position> findByCarOptionIds(List<Long> carOptionIds);
}
