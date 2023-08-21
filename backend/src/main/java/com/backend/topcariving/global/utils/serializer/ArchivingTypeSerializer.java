package com.backend.topcariving.global.utils.serializer;

import java.io.IOException;

import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ArchivingTypeSerializer extends JsonSerializer<ArchivingType> {
	@Override
	public void serialize(ArchivingType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
}
