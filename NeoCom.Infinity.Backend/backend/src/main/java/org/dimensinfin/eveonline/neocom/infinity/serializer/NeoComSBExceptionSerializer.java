package org.dimensinfin.eveonline.neocom.infinity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dimensinfin.eveonline.neocom.infinity.NeoComInfinityBackendApplication;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;

import java.io.IOException;

import javax.annotation.PostConstruct;

public class NeoComSBExceptionSerializer extends JsonSerializer<NeoComSBException> {
	@Override
	public void serialize( final NeoComSBException value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeNumberField("httpStatus", value.getHttpStatus().value());
		jgen.writeStringField("httpStatusName", value.getHttpStatus().name());
		jgen.writeStringField("message", value.getMessage());
		if (null != value.getRootException()) {
			jgen.writeStringField("exceptionType", value.getRootException().getClass().getSimpleName());
			jgen.writeStringField("exceptionMessage", value.getRootException().getMessage());
		}
		jgen.writeStringField("sourceClass", value.getSourceClass());
		jgen.writeStringField("sourceMethod", value.getSourceMethod());

		jgen.writeEndObject();
	}

	@PostConstruct
	private void register() {
		NeoComInfinityBackendApplication.registerSerializer(this.getClass(), this);
	}
}
