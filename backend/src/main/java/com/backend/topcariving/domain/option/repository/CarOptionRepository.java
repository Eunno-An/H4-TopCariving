package com.backend.topcariving.domain.option.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.option.entity.CarOption;

public interface CarOptionRepository {

	Optional<CarOption> findByCarOptionId(Long carOptionId);

	List<CarOption> findByParentOptionId(Long parentOptionId);

	List<CarOption> findByCategory(String category);

	List<CarOption> findByCategoryAndParentOptionIdIsNull(String category);

	List<CarOption> findByCategoryDetail(String categoryDetail);

	List<CarOption> findByCategoryDetailAndParentOptionIdIsNull(String categoryDetail);

	boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, String categoryDetail);

	List<CarOption> findByIds(List<Long> ids);

	List<CarOption> findByArchivingId(Long archivingId);

	List<String> findStringByParentOptionId(Long carOptionId);
}
