package com.backend.topcariving.domain.option.repository;

import java.util.Optional;

import com.backend.topcariving.domain.option.entity.EngineDetail;

public interface EngineDetailRepository {
	Optional<EngineDetail> findByCarOptionId(Long carOptionId);
}
