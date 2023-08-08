package com.backend.topcariving.domain.archive.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.archive.entity.MyCar;

@Repository
public interface MyCarRepository extends CrudRepository<MyCar, Long> {
}
