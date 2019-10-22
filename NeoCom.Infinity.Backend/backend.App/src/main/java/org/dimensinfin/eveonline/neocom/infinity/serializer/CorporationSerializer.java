package org.dimensinfin.eveonline.neocom.infinity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.eveonline.neocom.domain.Corporation;

@JsonComponent
public class CorporationSerializer extends JsonSerializer<Corporation> {
	@Override
	public void serialize( final Corporation value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField( "jsonClass", value.getJsonClass() );
		jgen.writeNumberField( "corporationId", value.getCorporationId() );
		jgen.writeObjectField(  "corporationPublicData", value.getCorporationPublicData() );
		jgen.writeObjectField(  "ceoPilotData", value.getCeoPilotData() );
		jgen.writeNumberField( "allianceId", value.getAllianceId() );
		jgen.writeObjectField(  "alliance", value.getAlliance() );
		jgen.writeStringField( "url4Icon", value.getUrl4Icon() );

		jgen.writeEndObject();
	}
}
