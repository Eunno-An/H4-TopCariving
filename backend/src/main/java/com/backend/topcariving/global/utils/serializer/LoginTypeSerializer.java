package com.backend.topcariving.global.utils.serializer;

import java.io.IOException;

import com.backend.topcariving.global.auth.entity.enums.LoginType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LoginTypeSerializer extends JsonSerializer<LoginType> {
	@Override
	public void serialize(LoginType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getName());
	}
}
