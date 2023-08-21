package com.backend.topcariving.domain.repository.option;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.dto.archive.response.PositionDTO;
import com.backend.topcariving.domain.entity.option.Position;

public interface PositionRepository {

	Optional<Position> findByCarOptionId(Long carOptionId);

	List<PositionDTO> findPositionDTOByCarOptionIds(List<Long> carOptionIds);
}
