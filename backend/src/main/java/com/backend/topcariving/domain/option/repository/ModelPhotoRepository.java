package com.backend.topcariving.domain.option.repository;

import java.util.List;

import com.backend.topcariving.domain.option.entity.ModelPhoto;

public interface ModelPhotoRepository {

	List<ModelPhoto> findAllByCarOptionId(Long carOptionId);
}
