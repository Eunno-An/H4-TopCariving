package com.backend.topcariving.domain.option.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Table("CAR_OPTION")
public class CarOption {

	@Id
	private Long carOptionId;

	private String category;

	private String categoryDetail;

	private String optionName;

	private String optionDetail;

	private int price;

	private String photoUrl;

	private Long parentOptionId;
}
