package com.backend.topcariving.domain.repository.option;

import java.util.List;

import com.backend.topcariving.domain.entity.option.ModelPhoto;

public interface ModelPhotoRepository {

	List<ModelPhoto> findAllByCarOptionId(Long carOptionId);
}
