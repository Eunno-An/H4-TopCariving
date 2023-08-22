package com.backend.topcariving.domain.repository.option;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;

public interface CarOptionRepository {

	Optional<CarOption> findByCarOptionId(Long carOptionId);

	List<CarOption> findByParentOptionId(Long parentOptionId);

	List<CarOption> findByCategory(Category category);

	List<CarOption> findByCategoryAndParentOptionIdIsNull(Category category);

	List<CarOption> findByCategoryDetail(CategoryDetail categoryDetail);

	List<CarOption> findByCategoryDetailAndParentOptionIdIsNull(CategoryDetail categoryDetail);

	boolean existsByCarOptionIdAndCategoryDetail(Long carOptionId, CategoryDetail categoryDetail);

	List<CarOption> findByIds(List<Long> ids);

	List<CarOption> findByArchivingId(Long archivingId);

	List<String> findStringByParentOptionId(Long carOptionId);

	Optional<CarOption> findByArchivingIdAndCategoryDetail(Long archivingId, CategoryDetail categoryDetail);
}
