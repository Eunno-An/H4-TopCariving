package com.backend.topcariving.domain.option.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.entity.ModelPhoto;

@Repository
public interface ModelPhotoRepository extends CrudRepository<ModelPhoto, Long> {

	List<ModelPhoto> findAllByCarOptionId(Long carOptionId);
}
