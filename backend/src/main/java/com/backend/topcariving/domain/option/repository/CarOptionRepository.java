package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.CarOption;

@Repository
public interface CarOptionRepository extends CrudRepository<CarOption, Long> {

	Optional<CarOption> findByCarOptionId(Long carOptionId);

	List<CarOption> findByParentOptionId(Long parentOptionId);

	List<CarOption> findByCategory(String category);

	List<CarOption> findByCategoryDetail(String categoryDetail);

	List<CarOption> findByCategoryDetailAndParentOptionId(@Param("categoryDetail") String categoryDetail, Long parentOptionId);

	boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, String categoryDetail);
}
