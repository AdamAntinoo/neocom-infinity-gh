package org.dimensinfin.eveonline.neocom.infinity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

//@JsonComponent
public class CredentialSerializer extends JsonSerializer<Credential> {
	@Override
	public void serialize( final Credential value, final JsonGenerator jgen, final SerializerProvider provider )
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();

		jgen.writeStringField("jsonClass", value.getJsonClass());
		jgen.writeStringField("uniqueId", value.getUniqueId());
		jgen.writeNumberField("accountId", value.getAccountId());
		jgen.writeStringField("accountName", value.getAccountName());
		jgen.writeStringField("dataSource", value.getDataSource());

		jgen.writeEndObject();
	}
}
