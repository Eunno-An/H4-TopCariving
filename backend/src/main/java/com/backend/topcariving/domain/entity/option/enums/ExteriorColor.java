package com.backend.topcariving.domain.entity.option.enums;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExteriorColor {

	ABYSS(11L, "abyss"),
	BLUE(12L, "blue"),
	BROWN(13L, "blown"),
	GRAY(14L, "gray"),
	SILVER(15L, "silver"),
	WHITE(16L, "white");

	private final Long carOptionId;
	private final String name;

	private static final String FIX_LINK = "https://d37c20hjk1kqzz.cloudfront.net/360/%s/image_001.png";

	public static String getPhotoUrl(Long carOptionId) {
		String colorName = Arrays.stream(ExteriorColor.values())
			.filter(exteriorColor -> Objects.equals(carOptionId, exteriorColor.getCarOptionId()))
			.findFirst().orElse(ABYSS).getName();
		return String.format(FIX_LINK, colorName);
	}
}
