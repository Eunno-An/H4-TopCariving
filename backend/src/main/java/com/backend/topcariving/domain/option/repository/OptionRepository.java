package com.backend.topcariving.domain.option.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.Option;

@Repository
public interface OptionRepository extends CrudRepository<Option, Long> {
	public List<Option> findByCategoryDetail(String categoryDetail);
}
