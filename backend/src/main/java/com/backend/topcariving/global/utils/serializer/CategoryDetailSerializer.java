package com.backend.topcariving.global.utils.serializer;

import java.io.IOException;

import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CategoryDetailSerializer extends JsonSerializer<CategoryDetail> {
	@Override
	public void serialize(CategoryDetail value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
}
