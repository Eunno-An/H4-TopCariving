package com.backend.topcariving.domain.repository.option;

import java.util.Optional;

import com.backend.topcariving.domain.entity.option.EngineDetail;

public interface EngineDetailRepository {
	Optional<EngineDetail> findByCarOptionId(Long carOptionId);
}
