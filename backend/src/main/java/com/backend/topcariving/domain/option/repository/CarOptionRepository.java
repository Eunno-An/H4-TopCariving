package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.CarOption;

@Repository
public interface CarOptionRepository extends CrudRepository<CarOption, Long> {

	Optional<CarOption> findByCarOptionId(Long carOptionId);

	List<CarOption> findByParentOptionId(Long parentOptionId);

	List<CarOption> findByCategoryDetail(String categoryDetail);

	boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, String categoryDetail);
}
