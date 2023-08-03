package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.dto.OptionResponseDTO;
import com.backend.topcariving.domain.option.entity.Option;
import com.backend.topcariving.domain.option.repository.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionService {

	private final OptionRepository optionRepository;

	@Transactional(readOnly = true)
	public List<OptionResponseDTO> getModels() {
		List<Option> options = optionRepository.findByCategoryDetail("모델");

		return options.stream()
			.map(OptionResponseDTO::from)
			.collect(Collectors.toList());
	}
}
