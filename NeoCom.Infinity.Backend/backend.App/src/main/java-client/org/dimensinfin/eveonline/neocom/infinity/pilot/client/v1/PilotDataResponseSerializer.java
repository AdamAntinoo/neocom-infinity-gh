package org.dimensinfin.eveonline.neocom.infinity.pilot.client.v1;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class PilotDataResponseSerializer extends JsonSerializer<PilotDataResponse> {
	@Override
	public void serialize( final PilotDataResponse value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField("jsonClass", "PilotDataResponse");
		jgen.writeNumberField("id", value.getId());
		jgen.writeNumberField("corporationId", value.getCorporationId());
		jgen.writeStringField("name", value.getName());
		jgen.writeStringField("gender", value.getGender().name());

		jgen.writeEndObject();
	}
}
