package com.backend.topcariving.global.utils.serializer;

import java.io.IOException;

import com.backend.topcariving.domain.entity.option.enums.Category;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CategorySerializer extends JsonSerializer<Category> {
	@Override
	public void serialize(Category value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
}
