package com.backend.topcariving.domain.option.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.CarOption;

@Repository
public interface CarOptionRepository extends CrudRepository<CarOption, Long> {
	List<CarOption> findByCategoryDetail(String categoryDetail);
}
