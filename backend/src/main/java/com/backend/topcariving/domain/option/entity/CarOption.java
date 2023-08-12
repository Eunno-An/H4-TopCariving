package com.backend.topcariving.domain.option.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarOption {

	private Long carOptionId;

	private String category;

	private String categoryDetail;

	private String optionName;

	private String optionDetail;

	private int price;

	private String photoUrl;

	private Long parentOptionId;
}
