package org.dimensinfin.eveonline.neocom.infinity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComNotFoundException;

@JsonComponent
public class NeoComNotFoundExceptionSerializer extends JsonSerializer<NeoComNotFoundException> {
	@Override
	public void serialize( final NeoComNotFoundException value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeNumberField("httpStatus", value.getHttpStatus().value());
		jgen.writeStringField("httpStatusName", value.getHttpStatus().name());
		jgen.writeStringField("message", value.getMessage());
		final String classLongName = value.getSourceClass();
		final String[] nameParts = classLongName.split( "\\." );
		final String className = nameParts[nameParts.length-1];
		jgen.writeStringField("sourceClass", className);
		jgen.writeStringField("sourceMethod", value.getSourceMethod());

		jgen.writeEndObject();
	}
}
