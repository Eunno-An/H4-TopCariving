package com.backend.topcariving.domain.entity.option;

import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.global.utils.serializer.CategoryDetailSerializer;
import com.backend.topcariving.global.utils.serializer.CategorySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarOption {

	private Long carOptionId;

	@JsonSerialize(using = CategorySerializer.class)
	private Category category;

	@JsonSerialize(using = CategoryDetailSerializer.class)
	private CategoryDetail categoryDetail;

	private String optionName;

	private String optionDetail;

	private int price;

	private String photoUrl;

	private Long parentOptionId;
}
