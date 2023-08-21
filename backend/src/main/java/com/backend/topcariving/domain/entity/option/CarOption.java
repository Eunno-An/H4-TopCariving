package com.backend.topcariving.domain.entity.option;

import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarOption {

	private Long carOptionId;

	private Category category;

	private CategoryDetail categoryDetail;

	private String optionName;

	private String optionDetail;

	private int price;

	private String photoUrl;

	private Long parentOptionId;
}
