package com.backend.topcariving.domain.entity.option.enums;

import static com.backend.topcariving.domain.entity.option.enums.CategoryDetail.*;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
	TRIM("트림"),
	BASIC_OPTION("기본 포함 품목"),
	CHOICE("선택품목"),
	EMPTY("empty");

	private final String name;

	public static Category valueOfName(String name) {
		return Arrays.stream(Category.values())
			.filter(category -> Objects.equals(name, category.getName()))
			.findFirst()
			.orElse(EMPTY);
	}

	public static String findCarOptionResultKey(CategoryDetail categoryDetail, Category category) {
		if (categoryDetail == MODEL)
			return MODEL.getName();
		else if (categoryDetail == INTERIOR_COLOR)
			return INTERIOR_COLOR.getName();
		else if (categoryDetail == EXTERIOR_COLOR)
			return EXTERIOR_COLOR.getName();
		else if (category == CHOICE)
			return CHOICE.getName();
		return TRIM.getName();
	}
}
