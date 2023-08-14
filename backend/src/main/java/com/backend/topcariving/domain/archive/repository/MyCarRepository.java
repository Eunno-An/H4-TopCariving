package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;

public interface MyCarRepository {

	MyCar save(MyCar myCar);

	Optional<MyCar> findByArchivingIdAndCarOptionId(Long archivingId, Long carOptionId);

	List<MyCar> findByArchivingId(Long archivingId);

	List<OptionSummaryDTO> findOptionSummaryByArchivingId(Long archivingId);

	void deleteByArchivingIdAndCategoryDetail(Long archivingId, String categoryDetail);

	void saveMultipleData(List<MyCar> myCars);
}
