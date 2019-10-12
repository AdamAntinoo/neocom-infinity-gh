package org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class CorporationDataResponseSerializer extends JsonSerializer<CorporationDataResponse> {
	@Override
	public void serialize( final CorporationDataResponse value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField("jsonClass", "CorporationDataResponse");
		jgen.writeNumberField("ceoId", value.getCeoId());
		jgen.writeObjectField("ceoPilotData", value.getCeoPilotData());
		jgen.writeStringField("name", value.getName());
		jgen.writeStringField("description", value.getDescription());
		jgen.writeNumberField("memberCount", value.getMemberCount());

		jgen.writeEndObject();
	}
}
