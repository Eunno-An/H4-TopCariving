package com.backend.topcariving.global.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
 import com.backend.topcariving.domain.option.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.exception.InvalidCategoryException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Validator {

	private final CarArchivingRepository carArchivingRepository;
	private final CarOptionRepository carOptionRepository;

	public void verifyCarArchiving(Long userId, Long archivingId) {
		if (!carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId)) {
			throw new InvalidAuthorityException();
		}
	}

	public void verifyCarOptionId(CategoryDetail categoryDetail, Long carOptionId) {
		if (!carOptionRepository.existsByCarOptionIdAndCategoryDetail(carOptionId, categoryDetail.getName())) {
			throw new InvalidCarOptionIdException();
		}
	}

	public void verifySameCategory(List<CarOption> carOptions, CategoryDetail categoryDetail) {
		for (CarOption option : carOptions) {
			if (!option.getCategoryDetail().equals(categoryDetail.getName()))
				throw new InvalidCategoryException();
		}
	}

	public void verifyArchivingId(Long archivingId) {
		if (!carArchivingRepository.existsByArchivingId(archivingId)) {
			throw new InvalidArchivingIdException();
		}
	}
}
