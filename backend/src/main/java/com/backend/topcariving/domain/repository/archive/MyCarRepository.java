package com.backend.topcariving.domain.repository.archive;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.dto.option.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;

public interface MyCarRepository {

	Optional<MyCar> findById(Long myCarId);

	MyCar save(MyCar myCar);

	Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);

	List<MyCar> findByArchivingId(Long archivingId);

	List<OptionSummaryDTO> findOptionSummaryByArchivingId(Long archivingId);

	void deleteByArchivingIdAndCategoryDetail(Long archivingId, CategoryDetail categoryDetail);

	void saveMultipleData(List<MyCar> myCars);

	void updateCarOptionIdByArchivingIdAndCategoryDetail(Long archivingId, Long carOptionId, CategoryDetail categoryDetail);

	List<MyCar> findByCategoryDetailAndArchivingId(CategoryDetail categoryDetail, Long archivingId);
}
