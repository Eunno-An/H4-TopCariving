package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;

public interface MyCarRepository {

	Optional<MyCar> findById(Long myCarId);

	MyCar save(MyCar myCar);

	Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);

	List<MyCar> findByArchivingId(Long archivingId);

	Optional<MyCar> findByArchivingIdAndCarOptionIdIsNull(Long archivingId);

	List<OptionSummaryDTO> findOptionSummaryByArchivingId(Long archivingId);

	List<Long> findArchivingIdByCarOptionId(List<Long> carOptionIds);

	void deleteByArchivingIdAndCategoryDetail(Long archivingId, String categoryDetail);

	void saveMultipleData(List<MyCar> myCars);

	void updateCarOptionIdByArchivingIdAndCategoryDetail(Long archivingId, Long carOptionId, String categoryDetail);

	List<MyCar> findByCategoryDetailAndArchivingId(String categoryDetail, Long archivingId);
}
