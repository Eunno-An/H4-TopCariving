package com.backend.topcariving.domain.option.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.EngineDetail;

@Repository
public interface EngineDetailRepository extends CrudRepository<EngineDetail, Long> {
}
