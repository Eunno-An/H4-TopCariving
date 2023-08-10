package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingOptionResponseDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimationService {

	private final CarArchivingRepository carArchivingRepository;
	private final MyCarRepository myCarRepository;
	private final CarOptionRepository carOptionRepository;

	@Transactional(readOnly = true)
	public ArchivingResponseDTO getArchivingResult(Long userId, Long archivingId) {
		verifyCarArchiving(userId, archivingId);

		List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);

		Map<CategoryDetail, ArchivingOptionResponseDTO> archivingOptionResponseDTOs = new HashMap<>();
		Map<CategoryDetail, ArchivingColorResponseDTO> archivingColorResponseDTOs = new HashMap<>();
		List<ArchivingOptionResponseDTO> selectOptionResponseDTOs = new ArrayList<>();

		for (MyCar myCar : myCars) {
			CarOption carOption = carOptionRepository.findByCarOptionId(myCar.getCarOptionId())
				.orElseThrow(InvalidCarOptionIdException::new);

			switch(valueOfName(carOption.getCategoryDetail())) {
				case MODEL:
					archivingOptionResponseDTOs.put(MODEL, ArchivingOptionResponseDTO.from(carOption));
					break;
				case ENGINE:
					archivingOptionResponseDTOs.put(ENGINE, ArchivingOptionResponseDTO.from(carOption));
					break;
				case BODY_TYPE:
					archivingOptionResponseDTOs.put(BODY_TYPE, ArchivingOptionResponseDTO.from(carOption));
					break;
				case DRIVING_METHOD:
					archivingOptionResponseDTOs.put(DRIVING_METHOD, ArchivingOptionResponseDTO.from(carOption));
					break;
				case EXTERIOR_COLOR:
					archivingColorResponseDTOs.put(EXTERIOR_COLOR, ArchivingColorResponseDTO.from(carOption));
					break;
				case INTERIOR_COLOR:
					archivingColorResponseDTOs.put(INTERIOR_COLOR, ArchivingColorResponseDTO.from(carOption));
					break;
				case SELECTED:
				case H_GENUINE_ACCESSORIES:
				case N_PERFORMANCE:
					selectOptionResponseDTOs.add(ArchivingOptionResponseDTO.from(carOption));
					break;
				default:
					throw new InvalidCarOptionIdException();
			}
		}

		return ArchivingResponseDTO.of(archivingId, archivingOptionResponseDTOs, archivingColorResponseDTOs, selectOptionResponseDTOs);
	}

	private void verifyCarArchiving(Long userId, Long archivingId) {
		if(!carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId)) {
			throw new InvalidAuthorityException();
		}
	}
}
